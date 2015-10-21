package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
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
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Dominic
 */
@WebServlet(urlPatterns={"/Logout"})
public class Logout extends HttpServlet{
    
     protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {  
         
         RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
	 rd.forward(request,response);

         //response.sendRedirect("index.jsp");
         
         //HttpSession session=request.getSession();  
         //session.invalidate(); 
            
            
     }
    
}
