/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 *
 * @author Administrator
 */
public class Pic {

    private String date;
    private String follower;
    private ByteBuffer bImage = null;
    private int length;
    private String type;
    private java.util.UUID UUID=null;
    private LinkedList<ComStore> comments;
    
    public void Pic() {

    }
    public void setUUID(java.util.UUID UUID){
        this.UUID =UUID;
    }
    public String getSUUID(){
        return UUID.toString();
    }
    public void setPic(ByteBuffer bImage, int length,String type) {
        this.bImage = bImage;
        this.length = length;
        this.type=type;
    }

    public ByteBuffer getBuffer() {
        return bImage;
    }

    public int getLength() {
        return length;
    }
    
    public String getType(){
        return type;
    }

    public byte[] getBytes() {
         
        byte image[] = Bytes.getArray(bImage);
        return image;
    }
    
     public void setDate(String date) {

        this.date = date;
    }

    public String getDate() {

        return date;
    }

    public void setFollower(String follower) {

        this.follower = follower;
    }

    public String getFollower() {

        return follower;
    }

    public LinkedList getComments() {
        return comments;
    }

    public void setComments(LinkedList comments) {
        this.comments = comments;
    }

}
