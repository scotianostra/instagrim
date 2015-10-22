<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="/InstaDom/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="/InstaDom/css/Styles.css" />
        
        <link href="http://fonts.googleapis.com/css?family=Oleo+Script" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="/InstaDom/js/bootstrap.js"></script>
    </head>
    
    <body>
        
        <nav class="navbar">
            <ul>
            <div class="navbar-brand text-center col-md-4">
                <a class="navbar-brand" href="/InstaDom">Instagrim</a>
            </div>
                
            <div class="navbar-search text-center col-md-4">
             <form class = "navbar-form" role = "search">
         
                 <div class = "form-group">
                 <input type = "text" class = "form-control" placeholder = "Search for users">
                 </div>
                 <button type = "submit" class = "btn btn-default">Search</button>
         
             </form>    
            </div>
                
            <div class="navbar-status navbar-brand text-center col-md-4">
                
                 <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>

                    <a class="navbar-status" href="/InstaDom/Images/<%=lg.getUsername()%>"><%=UserName%></a>
                    <a class="navbar-status" href="Logout">Log Out</a>
                    <%}
                            }else{
                                %>
                <a class="navbar-status" href="login.jsp">Log In</a>
                <%
                                        
                            
                    }%>
                
               
            </div>
            </ul>
        </nav> 
       
        <div class="container"> 
            
            <br>
            <br>
            <br>
            <br>
            
            <div class="col-md-4 border centre-div">
        
            <h2 class="text-center">Register</h2>
            <form method="POST"  action="Register">
                <div class="form-group">
                    <label>User Name</label>
                    <input type="text" class="form-control" name="username" placeholder="Name">
                 </div>
                
                 <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="password" placeholder="Password">
                 </div>
                <div class="text-center">
                <button type="submit" class="btn btn-primary">Register</button>
                </div>
            </form>
                
            </div>

        
        <footer>
            
        </footer>
            
        </div>
    </body>
</html>
