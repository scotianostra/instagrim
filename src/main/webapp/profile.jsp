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
         <%UserProfile u = (UserProfile) request.getAttribute("profilepic"); %>
        
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
                    <a class="navbar-status" href="/InstaDom/upload.jsp">Add Pic</a>
                    <a class="navbar-status" href="/InstaDom/profile.jsp">Profile</a>
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
           
           <div class="text-center centre-div col-md-4">
               
               <h1>Edit your Profile</h1>
               
           </div>
           
           <div class="col-md-4 text-center profile-photo-container">
               
                <img src="/InstaDom/Image/<%=u.getUUID()%>" class="profile-photo img-circle show-in-modal"/>
                <h4 class="text-white text-center name" id="usrName"><%=lg.getUsername()%></h4>                        
                <hr class="name-separator">
           
                <form class="form-horizontal" method="POST" enctype="multipart/form-data" action="Image">
                    <h4>Upload a profile picture</h4>
                
                    <div class="form-group">
                      <input type="file" name="upfile">
                     </div>
                
                     <input class="btn btn-primary pull-right" type="submit" value="Press">
                </form>
           </div>
           
           <div>
                <form class="navbar-form" method="POST">                    
                
                    <div class="input-group" style="display:table;">
                        First Name: <input class="form-control" autocomplete="off" autofocus="autofocus" type="text" id="inptFirstName" value="<%= u.getFirstname()%>">
                        <span class="input-group-addon" style="width:1%;">
                        <span class=""></span>
                        </span>
                    </div>
                        
                        <br/>
                        
                    <div class="input-group" style="display:table;">
                        Last Name: <input class="form-control"  autofocus="autofocus" type="text" id="inptLastName" value="<%= u.getLastname()%>">
                        <span class="input-group-addon" style="width:1%;">
                        <span class=""></span>
                        </span>
                    </div>
                        
                        <br/>
                        
                    <div class="input-group" style="display:table;">
                        Email: <input class="form-control" data-validate="required"  autofocus="autofocus" type="email" id="inptEmail" value="<%= u.getEmail()%>">
                        <span class="input-group-addon" style="width:1%;">
                        <span class=""></span>
                        </span>
                    </div>  
                        
                        <br/>
                        
                    <div class="pull-right">
                        <button type='button' class='btn btn-finish btn-fill btn-sky btn-wd btn-sm' id="btnUpdate">Finish</button>
                    </div>
               
                </form>
           </div>
               
               
               
           </div>
           
       </div>
           
    
       
    </body>
</html>
