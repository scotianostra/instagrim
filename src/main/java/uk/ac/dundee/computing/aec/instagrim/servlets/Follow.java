/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.UserProfile;

/**
 *
 * @author Dominic
 */
@WebServlet(name = "Follow", urlPatterns = {"/Follow", "/UnFollow"})
public class Follow extends HttpServlet  {
    
    Cluster cluster=null;
    
     @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        //String requestParts[] = Convertors.SplitRequestPath(request);
       // System.out.println("follow path " + requestParts[1]);
        //System.out.println("request path " + requestParts[0]);
       
       // String follow = requestParts[1];
        //String requestPath = requestParts[0];

        HttpSession session = request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
        
        String currentUser = lg.getUsername();
        String toFollow = (String) session.getAttribute("profileName");
        Boolean following = (Boolean) session.getAttribute("following");
        System.out.println("Current User " + currentUser);
        System.out.println("To Follow " + toFollow);
        System.out.println("Follow/Unfollow " + following);
             
        
         
        User us = new User();
        us.setCluster(cluster);
        
        
        if (following) {

            us.unFollow(currentUser, toFollow);
            System.out.println("Called Follow");

        } else if (!following) {

            us.follow(currentUser, toFollow);
            System.out.println("Called unFollow");
        }
        
        response.sendRedirect("/InstaDom/Images/" + toFollow);
        
    }
    
}
