/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dominic
 */
public class ComStore {
    
    private String comment;
    private String commenter;
    private Date time;
    
     public String getComment() {
        return comment;
    }

    public String getCommenter() {
        return commenter;
    }
   

    public String getTime() {
        DateFormat date = new SimpleDateFormat("YYYY MMM dd, HH:mm");
        return date.format(time);
    }
    
    public void setCom(String commenter,String comment,Date time) {
        this.commenter = commenter;
        this.comment = comment;
        this.time = time;
    }
}