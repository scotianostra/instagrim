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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.*;

/**
 *
 * @author Dominic
 */
@WebServlet(name = "Profile", urlPatterns = {"/Profile"})
public class Profile extends HttpServlet {
    
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
        String usname = lg.getUsername();
        System.out.println("username " + usname);
        
        User us = new User();
        us.setCluster(cluster);
        UserProfile up = us.getUserProfile(usname);
        
        if(up.getFirstname() == null){
            up.setFirstname("");
            }
        if(up.getLastname() == null){
            up.setLastname("");
            }
        if(up.getBio() == null){
            up.setBio("");
            }
        
        request.setAttribute("username", usname);
        request.setAttribute("UserProfile", up);
        //System.out.println("Fname " + up.getFirstname());
        //System.out.println("LName " + up.getLastname());
        //System.out.println("uuid:NNNN " + up.getUUID());
        //System.out.println("Profile data " + up.getBio());
        
        RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
        
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String bio = request.getParameter("bio");
        
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");        
        String username =lg.getUsername();        
       
        User us = new User();
        us.setCluster(cluster);
        us.UpdateProfile(firstname, lastname, bio, username);
        
        response.sendRedirect("/InstaDom/Profile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}