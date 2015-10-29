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
               <%LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>

        <nav class="navbar">
            <ul>
            <div class="navbar-brand text-center col-md-4">
                <%
                
                        if (lg == null) {
                            
                %>    
                    <a class="navbar-brand" href="/InstaDom">Instagrim</a>
                <%
                        }
                   else{
                 %>
                    
                <a class="navbar-brand" href="/InstaDom/Home">Instagrim</a>
                <%
                   }
                        %>
            </div>
                
            <div class="navbar-search text-center col-md-4">
                
                 <form class="navbar-form" method="post" action= "Search">         
                 <div class = "form-group">                    
                     <input type="text" name="username" class="form-control" placeholder="${sessionScope.param}" required autofocus>
                 
                 </div>
                 <form>
                 
                 <button type = "submit" class = "btn btn-default">Search</button>
         
             </form>    
            </div>
                
            <div class="navbar-status navbar-brand text-center col-md-4">
                
                 <%
                        if (lg != null){
                        
                            if (lg.getlogedin()) {
                                String UserName = lg.getUsername();
                    %>

                    <a class="navbar-status" href="/InstaDom/Images/<%=lg.getUsername()%>"><%=UserName%></a>
                    <a class="navbar-status" href="/InstaDom/Home/<%=lg.getUsername()%>">Home</a> 
                    <a class="navbar-status" href="/InstaDom/Logout">Log Out</a>
                    <%}
                        }else{
                                %>
                <a class="navbar-status" href="/InstaDom">Log In</a>
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
                    <input type="text" class="form-control" name="username" placeholder="">
                 </div>
                
                <% if (request.getAttribute("taken") != null) { %>
                
                 <div class="form-group">
                <h5>*Username already taken, please try again</h5>
                </div>
                <%}%>
                
                 <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="password" placeholder="Password">
                 </div>
                
                
                    <label>Email</label>
                    <input type="text" class="form-control" name="email" placeholder="email"  <% if (request.getAttribute("email") != null) {%> value="<%=request.getAttribute("email")%>" <%}%> >
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

