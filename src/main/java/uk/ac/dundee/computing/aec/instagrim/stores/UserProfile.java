/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import java.util.UUID;

/**
 *
 * @author Dominic
 */
public class UserProfile {
    
    private String username = null;
    private String firstname = null;
    private String lastname = null;
    private UUID profilepic = null;
    private String bio = null;
    private String email = null;
    

    public void UserProfile() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
     public UUID getUUID() {
        return profilepic;
    }

    public void setUUID(UUID id) {
        this.profilepic = id;
    }
    
}
