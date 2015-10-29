package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dominic
 */
@WebServlet(name = "Logout", urlPatterns={"/Logout"})
public class Logout extends HttpServlet{
    
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {  
         
         HttpSession session = request.getSession();
         
         session.removeAttribute("LoggedIn");
         session.removeAttribute("username");
         
         session.invalidate();
         
         response.sendRedirect("/InstaDom");
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

