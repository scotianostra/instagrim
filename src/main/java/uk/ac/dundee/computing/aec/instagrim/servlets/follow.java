/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.LinkedList;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.*;

/**
 *
 * @author Dominic
 */
@WebServlet(name = "Follow", urlPatterns = {"/Follow", "/UnFollow"})
public class follow extends HttpServlet  {
    
    Cluster cluster=null;
    
     @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String requestParts[] = Convertors.SplitRequestPath(request);
        System.out.println("follow " + requestParts[2]);
        System.out.println("request " + requestParts[3]);
        String requestPath = requestParts[3];
        String follow = requestParts[2];

        HttpSession session = request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
        String currentUser = lg.getUsername();
         
        User us = new User();
        us.setCluster(cluster);
        
        
        if (requestPath.equals("Follow")) {

            us.follow(currentUser, follow);

        } else if (requestPath.equals("UnFollow")) {

            us.unFollow(currentUser, follow);
        }
        
    }
    
}
