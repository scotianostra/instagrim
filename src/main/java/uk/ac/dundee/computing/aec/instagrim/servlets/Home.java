/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.UserProfile;

/**
 *
 * @author Dominic
 */
@WebServlet(name = "Home", urlPatterns = {"/Home/*"})
public class Home extends HttpServlet {
    
    Cluster cluster = null;
    private final HashMap CommandsMap = new HashMap();
    
    public Home() {
        super();
        // TODO Auto-generated constructor stub
       CommandsMap.put("Home", 1);
        
    }
    
    
    @Override 
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * @param request    
     * @param response    
     * @throws javax.servlet.ServletException    
     * @throws java.io.IOException    
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {      
                                   
            System.out.println("home doGet called");
            
            User us = new User();
            us.setCluster(cluster);
            
            HttpSession session = request.getSession();
            
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");         
            String username = lg.getUsername();
            
            UserProfile up = us.getUserProfile(username);
            request.setAttribute("UserProfile", up);
            
            DisplayHomepageImages(username, request, response);

        
    }
    
    private void DisplayHomepageImages(String username, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        User us = new User();
        us.setCluster(cluster);
        
        LinkedList<String> following = new LinkedList<String>();
        following = us.getFollowing(username);
        following.add(username);
            
        LinkedList<Pic> pics = tm.getHomePics(following);
        System.out.println("Linked List all following: " + following);

        request.setAttribute("Pics", pics);
        
        RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");        
        rd.forward(request, response);

    }
}

