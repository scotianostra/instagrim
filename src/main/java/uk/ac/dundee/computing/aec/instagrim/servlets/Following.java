/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author Dominic
 */
@WebServlet(name = "Follow", urlPatterns = {"/Follow", "/UnFollow"})
public class Following extends HttpServlet  {
    
    Cluster cluster=null;
    
     @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
