/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.UserProfile;

/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
    public boolean RegisterUser(String username, String Password, String email){
       AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;

        try {

            EncodedPassword = sha1handler.SHA1(Password);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {

            System.out.println("Can't check your password");
            return false;

        }
        
        Session session = cluster.connect("instadom");
        PreparedStatement ps = session.prepare("insert into userprofiles (login, password, email) Values(?,?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(username, EncodedPassword, email));
        //We are assuming this always works.  Also a transaction would be good here !
        
        return true;
    }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instadom");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        } 
        return false;  
    }
    
    public boolean UpdateProfile(String firstname, String lastname, String bio, String username) {
        Session session = cluster.connect("instadom");
        PreparedStatement ps = session.prepare("UPDATE userprofiles SET first_name = ?, last_name = ?, bio = ? WHERE login = ?;");
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(firstname, lastname, bio, username));
        System.out.println(firstname + " " + lastname + " " + bio + " " + username);
        return true;
    }
    
    public UserProfile getUserProfile(String username) {

        UserProfile data = new UserProfile();
        Session session = cluster.connect("instadom");
        PreparedStatement ps1 = session.prepare("select login, first_name, last_name, email, bio, picid from userprofiles where login =?");
        
        ResultSet rs = null;
        
        System.out.println("Work mother fucker!");
        
        BoundStatement boundStatement = new BoundStatement(ps1);

        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));

        if (rs.isExhausted()) {

            System.out.println("No profile found");
            data.setFirstname("");
            data.setLastname("");
            data.setEmail("");
            data.setBio("");
            data.setUUID(null);

        } else {

            for (Row row : rs) {

                data.setUsername(row.getString("login"));
                data.setFirstname(row.getString("first_name"));
                data.setLastname(row.getString("last_name"));
                data.setEmail(row.getString("email"));
                data.setBio(row.getString("bio"));
                data.setUUID(row.getUUID("picid"));

            }
        }
        
        System.out.println(data.getFirstname() + " " + data.getLastname() + " " + data.getEmail() + data.getBio() + " " + data.getUsername());

        return data;
    }
    
   public void follow(String currentUser, String followee) {
        
        Session session = cluster.connect("instadom");
        PreparedStatement ps1 = session.prepare("insert into following (login, following) Values(?,?)");
        
        BoundStatement boundStatement1 = new BoundStatement(ps1);
        session.execute(boundStatement1.bind(currentUser, followee ));
        
        PreparedStatement ps2 = session.prepare("insert into followers (login, follower) values(?,?)");
        
        BoundStatement boundStatement2 = new BoundStatement(ps2);
        session.execute(boundStatement2.bind(followee, currentUser));
        
    }
   
   public LinkedList<String> getFollowing(String username) {
        
        LinkedList<String> following = new LinkedList<>();
        Session session = cluster.connect("instadom");
      
        PreparedStatement ps = session.prepare("select following from following where login =?");       
        BoundStatement boundStatement = new BoundStatement(ps);
        
        ResultSet rs = null;
        rs = session.execute(boundStatement.bind(username));

        if (rs.isExhausted()) {

            System.out.println("Not following");
            return following;

        } else {

            for (Row row : rs) {
                String string = row.getString("following");
                following.add(string);
            }
        }       
        
        return following;
        
    }
    
    public void unFollow(String currentUser, String unFollowee) {
        
        Session session = cluster.connect("instadom");
        PreparedStatement ps1 = session.prepare("delete from following where login =? AND following =?");
        
        BoundStatement boundStatement1 = new BoundStatement(ps1);
        session.execute(boundStatement1.bind(currentUser, unFollowee ));
        
        PreparedStatement ps2 = session.prepare("delete from followers where login =? AND follower =?");
        BoundStatement boundStatement2 = new BoundStatement(ps2);
        session.execute(boundStatement2.bind(unFollowee, currentUser));
        
    }
    
     public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    
}