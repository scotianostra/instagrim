/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.LinkedList;
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
@WebServlet(name = "Search",  
urlPatterns = {
                "/Search",
                "/Images/Search",
                "/Home/Search",
                "/Profile/Search",
                "/Register/Search"
                  })
public class Search extends HttpServlet {

    Cluster cluster = null;
    String url = "/WEB-INF/view/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getRequestURL().toString();
        System.out.println("URL Path: " + url);
        
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn)session.getAttribute("LoggedIn");
               
        String searchName = (String) request.getParameter("username");        
        
        User us = new User();
        us.setCluster(cluster);
        LinkedList<String> users = us.getUsers();
                
        System.out.println("searched for username: " + searchName);

        if (users.contains(searchName)) {
             session.setAttribute("param", "Search for another user");
            response.sendRedirect("/InstaDom/Images/" + searchName);
        }
        else{
            
            session.setAttribute("param", "User not found, try again");
            if(lg != null){
                 String currentUser = lg.getUsername();
                 System.out.println("Current User: " + currentUser);
                 response.sendRedirect("/InstaDom/Images/" + currentUser);
            }
            else{
                 response.sendRedirect("/InstaDom");
            }
           
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
