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
                               
           
               
               <%
                        
                        if (profile == null) {                      
                    
               %>
               
                <div class="text-center centre-div col-md-5">
               
                    <h1>Complete your profile</h1> 
               
                </div>
               
                <div class="col-md-offset-1 col-md-5 border">
                
                <img src="/InstaDom/img/generic-avatar.png" class="profile-photo img-circle"/>                  
                
                <h2 class="text-center">Add Profile Picture</h2>
                <form method="POST" enctype="multipart/form-data" action="ProfilePic">
                    <div class="form-group block-center">                        
                        <input class="border1" type="file" name="file">
                    </div>
                    <div>
                           <label>Choose a filter</label>
                            <select required="required" name="filterChoice">
-                            <option value="Original">Original</option>
-                            <option value="Grayscale">Grayscale</option>
                             <option value="Brighter">Brighter</option>
-                            <option value="Darker">Darker</option>
-                           </select>
                    </div>
                    <div class="text-center">                                                               
                        <input class="btn btn-primary" type="submit" value="Upload">
                    </div>
                </form>
            </div>
               
                <div class="col-md-5 col-md-offset-1 border">
        
            <h2 class="text-center">Complete Profile</h2>
            <form method="POST"  action="/InstaDom/Profile">
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
                                                        
                            
                <div class="text-center centre-div col-md-5">
               
                    <h1>Edit your profile</h1> 
               
                </div>
                
                
            
            <div class="col-md-offset-1 col-md-5 border">
                
                  <%
            if (profile.getUUID() == null) {
                %>
            <img src="/InstaDom/img/generic-avatar.png" class="profile-photo img-circle"/>    
        <%}
             else
        {%>  
                 
            <img src="/InstaDom/Image/<%= profile.getUUID()%>" class="profile-photo img-circle"/>                
                      
         
         
        <%
         }
         %>
           
                
                
                <h2 class="text-center">Edit Profile Picture</h2>
                <form method="POST" enctype="multipart/form-data" action="ProfilePic">
                    <div class="form-group block-center">
                        <div>
                           <label>Choose a filter</label>
                            <select required="required" name="filterChoice">
-                            <option value="Original">Original</option>
                             <option value="Antialias">Antialias</option>
-                            <option value="Grayscale">Grayscale</option>
                             <option value="Brighter">Brighter</option>
-                            <option value="Darker">Darker</option>
-                           </select>
                        </div>
                        <input class="border1" type="file" name="file">
                    </div>
                    <div class="text-center">                                                               
                        <input class="btn btn-primary" type="submit" value="Upload">
                    </div>
                </form>
            </div>
            
           
           
            <div class="col-md-offset-1 col-md-5 border">
        
            <h2 class="text-center">Edit Profile Details</h2>
            <form method="POST"  action="/InstaDom/Profile">
                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" class="form-control" name="firstname" value="<%= profile.getFirstname()%>">
                 </div>
                
                 <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" class="form-control" name="lastname" value="<%= profile.getLastname()%>">
                 </div>
                <div class="form-group">
                    <label>Mini Bio</label>
                    <input type="text" class="form-control" name="bio" value="<%= profile.getBio()%>">
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
               
               
               
           
           
       
           
    
       
    </body>
</html>
