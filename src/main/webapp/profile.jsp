<%--
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>



<%@page import="java.util.*"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
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
         <%UserProfile profile = (UserProfile)request.getAttribute("UserProfile"); %>
        
        <nav class="navbar">
            <ul>
            <div class="navbar-brand text-center col-md-4">
                <a class="navbar-brand" href="/InstaDom">Instagrim</a>
            </div>
                
            <div class="navbar-search text-center col-md-4">
             <form class = "navbar-form" role = "search">
         
                 <div class = "form-group">
                 <input type = "text" class = "form-control" placeholder = "Search">
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
                    <a class="navbar-status" href="/InstaDom/Upload">Add Pic</a>
                    <a class="navbar-status" href="/InstaDom/Profile">Profile</a>
                    <a class="navbar-status" href="/InstaDom/Logout">Log Out</a>
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
                               
           
               
               <%
                        
                        if (profile.getFirstname() == "") {                      
                    
               %>
               
                <div class="text-center centre-div col-md-5">
               
                    <h1>You have not completed a profile</h1> 
               
                </div>
               
                <div class="col-md-5 border centre-div">
        
            <h2 class="text-center">Complete Profile</h2>
            <form method="POST"  action="Profile">
                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" class="form-control" name="firstname" placeholder="Name">
                 </div>
                
                 <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" class="form-control" name="lastname" placeholder="Last Name">
                 </div>
                <div class="form-group">
                    <label>Mini Bio</label>
                    <input type="text" class="form-control" name="bio" placeholder="Mini Bio">
                </div>
                <div class="text-center">
                <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </form>
            
            </div>
               
                <%
                }   else {
                %>
                            
                            } 
                            
                            
                <div class="text-center centre-div col-md-5">
               
                    <h1>Edit your profile</h1> 
               
                </div>
                
            <div class="col-md-3 border centre-div">
                
                <%--<img src="/InstaDom/Image/<%=up.getUUID()%>" class="profile-photo img-circle show-in-modal"/>
                <h4 class="text-center" id="usrName"><%=lg.getUsername()%></h4>                        
                <hr class="name-separator">--%>
           
                <form class="form-horizontal" method="POST" enctype="multipart/form-data" action="Image">
                    <h4>Upload a profile picture</h4>
                
                    <div class="form-group">
                      <input type="file" name="upfile">
                     </div>
                
                     <input class="btn btn-primary pull-right" type="submit" value="Press">
                </form>
           
           
            <div class="col-md-3 border centre-div">
        
            <h2 class="text-center">Edit Profile</h2>
            <form method="POST"  action="Profile">
                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" class="form-control" name="first_name" placeholder="<%= profile.getFirstname()%>">
                 </div>
                
                 <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" class="form-control" name="last_name" placeholder="<%= profile.getLastname()%>">
                 </div>
                <div class="form-group">
                    <label>Mini Bio</label>
                    <input type="text" class="form-control" name="bio" placeholder="<%= profile.getBio()%>">
                </div>
                <div class="text-center">
                <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </form>
            
            </div>
                        
               
                <%
                    }
                %>
           </div>
               
               
               
           </div>
           
       
           
    
       
    </body>
</html>
