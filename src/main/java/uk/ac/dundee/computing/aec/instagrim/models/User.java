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
        return true;
    }
    
    public UserProfile getUserInfo(String username) {

        UserProfile data = new UserProfile();
        Session session = cluster.connect("instadom");
        PreparedStatement ps = session.prepare("select * from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);

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

                data.setFirstname(row.getString("first_name"));
                data.setLastname(row.getString("last_name"));
                data.setEmail(row.getString("email"));
                data.setBio(row.getString("bio"));
                data.setUUID(row.getUUID("profilepic"));

            }
        }

        return data;
    }
    
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    
}
