package uk.ac.dundee.computing.aec.instagrim.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            String createkeyspace = "create keyspace if not exists instadom  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists instadom.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + "  processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid)"
                    + ")";
            String CreateIndexOnPics = "Create INDEX userpiclist ON instadom.Pics (user)";
            String Createuserpiclist = "CREATE TABLE if not exists instadom.userpiclist (\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "pic_added timestamp,\n"
                    + "PRIMARY KEY (user,pic_added)\n"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";
            String CreateIndexOnUserPicsList = "Create INDEX userlistPicid ON instaDom.userpiclist (picid)";
            String CreateAddressType = "CREATE TYPE if not exists instadom.address (\n"
                    + "      street text,\n"
                    + "      city text,\n"
                    + "      zip int\n"
                    + "  );";
            String CreateUserProfile = "CREATE TABLE if not exists instadom.userprofiles (\n"
                    + "      login text PRIMARY KEY,\n"
                    + "      password text,\n"
                    + "      first_name text,\n"
                    + "      last_name text,\n"
                    + "      email text,\n"
                    + "      bio text,\n"
                    + "      picid uuid,\n"
                    + "  );";
            String CreateFollowing = "CREATE TABLE if not exists instadom.following (\n"
                    + "      login text,\n"
                    + "      following text,\n"
                    + "      PRIMARY KEY (login, following)\n"
                    + "  );";
            String CreateFollowers = "CREATE TABLE if not exists instadom.followers (\n"
                    + "      login text,\n"
                    + "      follower text,\n"
                    + "      PRIMARY KEY (login, follower)\n"
                    + "  );";
            Session session = c.connect();
            try {
                PreparedStatement statement = session
                        .prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(
                        statement);
                ResultSet rs = session
                        .execute(boundStatement);
                System.out.println("created instagrim ");
            } catch (Exception et) {
                System.out.println("Can't create instagrim " + et);
            }

            //now add some column families 
            System.out.println("" + CreatePicTable);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }
            System.out.println("" + Createuserpiclist);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list table " + et);
            }
            System.out.println("" + CreateAddressType);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address type " + et);
            }
            System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address Profile " + et);
            }
            
            System.out.println("" + CreateFollowing);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateFollowing);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create following table " + et);
            }
            
            System.out.println("" + CreateFollowers);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateFollowers);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create following table " + et);
            }
            
            System.out.println("" + CreateIndexOnPics);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateIndexOnPics);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create index " + et);
                }
            
             System.out.println("" + CreateIndexOnUserPicsList);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateIndexOnUserPicsList);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create index " + et);
                }
            
                
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }

    }
}
