package uk.ac.dundee.computing.aec.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;
import java.util.UUID;
import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.ComStore;


public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(byte[] b, String type, String name, String user, boolean profpic, String filter) {
        try {
            Convertors convertor = new Convertors();

            String types[] = Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
            java.util.UUID picid = convertor.getTimeUUID();

            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/instaDom/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instaDom/" + picid));

            output.write(b);
            byte[] thumbb = picresize(picid.toString(), types[1], filter);
            int thumblength = thumbb.length;
            ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
            byte[] processedb = picdecolour(picid.toString(), types[1], filter);
            ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
            int processedlength = processedb.length;

            try (Session session = cluster.connect("instadom")) {

                Date DateAdded = new Date();

                PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, interaction_time,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
                BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
                session.execute(bsInsertPic.bind(picid, buffer, thumbbuf, processedbuf, user, DateAdded, length, thumblength, processedlength, type, name));

                if (!profpic) {
                    PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
                    BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);
                    session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
                } else {
                    PreparedStatement profilePic = session.prepare("update userprofiles SET picid = ? where login = ?;");
                    BoundStatement insertProfile = new BoundStatement(profilePic);
                    session.execute(insertProfile.bind(picid, user));
                    System.out.println("id inserted into picid " + picid);
                }
                session.close();
            }

        } catch (IOException ex) {
            System.out.println("Error --> " + ex);
        }
    }

    public void deletePic(String picid) {
        try (Session session = cluster.connect("instadom")) {

            ResultSet rs = null;
            PreparedStatement ps1 = session.prepare("select * from userpiclist where picid=?");
            BoundStatement boundStatement1 = new BoundStatement(ps1);
            rs = session.execute(boundStatement1.bind(java.util.UUID.fromString(picid)));

            String name = "";
            Date dateStamp = new Date();

            if (rs.isExhausted()) {
            } else {
                for (Row row : rs) {
                    name = row.getString("user");
                    dateStamp = row.getDate("pic_added");
                }
            }
            System.out.println("User: " + name + "time: " + dateStamp);

            PreparedStatement ps2 = session.prepare("delete from Pics where picid=?");
            BoundStatement boundStatement2 = new BoundStatement(ps2);
            session.execute(boundStatement2.bind(java.util.UUID.fromString(picid)));

            PreparedStatement ps3 = session.prepare("delete from userpiclist where user=? and pic_added=?");
            BoundStatement boundStatement3 = new BoundStatement(ps3);
            session.execute(boundStatement3.bind(name, dateStamp));

        }
    }

    public byte[] picresize(String picid, String type, String filter) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instaDom/" + picid));
            BufferedImage thumbnail = createThumbnail(BI, filter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();

            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public byte[] picdecolour(String picid, String type, String filter) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instaDom/" + picid));
            BufferedImage processed = createProcessed(BI, filter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public static BufferedImage createThumbnail(BufferedImage img, String filter) {
        if (filter.equals("Black and White")) {

            System.out.println("Filter in Thumbnail is: " + filter);
            img = resize(img, Method.SPEED, 300, OP_ANTIALIAS, OP_GRAYSCALE);
            return img;
        } else // Let's add a little border before we return result.
        {
            img = resize(img, Method.SPEED, 300);
            return img;
        }
    }

    public static BufferedImage createProcessed(BufferedImage img, String filter) {
        int Width = img.getWidth() - 1;

        System.out.println("Filter in Processed is: " + filter);

        
        switch (filter) {
            case "Grayscale":
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
                return img;
            case "Brighter":
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_BRIGHTER);
                return img;
            case "Darker":
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_DARKER);
                return img;
            default:
                img = resize(img, Method.SPEED, Width, OP_ANTIALIAS);
                return img;
        }
    }

    public java.util.LinkedList<Pic> getPicsForUser(String User) {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instadom");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                LinkedList<ComStore> comments = getComments(UUID);
                pic.setComments(comments);
                Pics.add(pic);

            }
        }
        return Pics;
    }

    public Pic getPic(int image_type, java.util.UUID picid) {
        Session session = cluster.connect("instadom");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        try {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;

            if (image_type == Convertors.DISPLAY_IMAGE) {

                ps = session.prepare("select image,imagelength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type from pics where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
                return null;
            } else {
                for (Row row : rs) {
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");

                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }

                    type = row.getString("type");

                }
            }
        } catch (Exception et) {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }

    public UUID getProfilePic(String username) {

        UUID profile = null;
        Session session = cluster.connect("instadom");
        PreparedStatement ps = session.prepare("select picid from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind(username));

        if (rs.isExhausted()) {
            System.out.println("Nothing returned");
            return null;
        } else {
            Row row = rs.one();
            profile = row.getUUID("picid");
        }
        return profile;
    }

    public LinkedList<Pic> getHomePics(LinkedList<String> following) {

        LinkedList<Pic> Pics = new LinkedList<>();
        Map<Date, Pic> picsMap = new HashMap();

        Session session = cluster.connect("instadom");

        for (String string : following) {

            PreparedStatement ps = session.prepare("select picid, pic_added from userpiclist where user =?");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(string));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
            } else {
                for (Row row : rs) {
                    Pic pic = new Pic();

                    java.util.UUID UUID = row.getUUID("picid");
                    pic.setUUID(UUID);
                    pic.setFollower(string);
                    String date = row.getDate("pic_added").toString();
                    pic.setDate(date);
                    LinkedList<ComStore> comments = getComments(UUID);
                    pic.setComments(comments);
                    picsMap.put(row.getDate("pic_added"), pic);
                }
            }
        }

        Map<Date, Pic> order = new TreeMap(picsMap).descendingMap();

        for (Pic pi : order.values()) {
            Pics.add(pi);
        }

        return Pics;
    }
    
    public void addComments(String commenter, String comment,UUID picid) {
        Session session = cluster.connect("instadom");
        Date date = new Date();

        PreparedStatement ps = session.prepare("INSERT into comments ( picid, commenter, time, comment) values(?,?,?,?)");
        BoundStatement bs = new BoundStatement(ps);
        session.execute(bs.bind(picid, commenter, date, comment));

        session.close();
    }

    public LinkedList<ComStore> getComments(UUID picid) {
        
        LinkedList<ComStore> comments = new LinkedList<>();
        Session session = cluster.connect("instadom");
        
        PreparedStatement ps = session.prepare("SELECT commenter,time,comment from comments where picid =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute(boundStatement.bind(picid));
        
        if (rs.isExhausted()) {
            System.out.println("No Comments returned");
            return null;
        } else {
            for (Row row : rs) {
                String commenter = row.getString("commenter");
                String comment = row.getString("comment");
                Date time = row.getDate("time");

                ComStore comms = new ComStore();
                comms.setCom(commenter, comment, time);

                comments.add(comms);
            }
        }
        return comments;
    }


}
