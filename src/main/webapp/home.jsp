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
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
       <%-- <script>function submitMyForm(){ document.forms["Follow"].submit();}</script>--%>
    </head>
        
   <body>
       
       <%UserProfile profile = (UserProfile) request.getAttribute("UserProfile"); %>
       <%session.setAttribute("profileName", (profile.getUsername()));%>
      
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
           
          
      <div class="col-md-4 profile-box-pic">
           
            <%
            if (profile.getUUID() == null) {
                %>
        <p>No profile picture uploaded</p>
        <%}
             else
        {%>  
                 
            <img src="/InstaDom/Image/<%= profile.getUUID()%>" class="profile-photo img-circle"/>                
                      
         
         
        <%
         }
         %>
           
            </div>
       
            
         <div class="col-md-8 profile-box">
             <div>
             <h1 class="username-title"><%= profile.getUsername()%></h1>
             </div>
             
                 
             <%
                if (lg != null)  {
                      if((lg.getProfileMatch() == false) && (!(lg.getUsername().equals( profile.getUsername() )))) {
                          session.setAttribute("following", false);
                          %>
                          <div>
                              <form method="get" action="${pageContext.request.contextPath}/Follow">
                                <button type="submit" class="btn btn-primary">Un Follow</button>  
                              </form>
                          </div>
                          
                          
                          
                           <%
                    }
                }
                      if (lg != null)  {
                        if((lg.getProfileMatch() == true) && (!(lg.getUsername().equals(profile.getUsername() )))) {
                              session.setAttribute("following", true); %>
                           <div> 
                            <form method="get"  id="Follow" action="${pageContext.request.contextPath}/Follow">
                           <button type="submit" class ="btn btn-primary">Follow</button> 
                            </form>  
                           </div> 
                           
                          <%
                        }
                      }
                        if (lg != null)  {
                            if(lg.getUsername().equals( profile.getUsername()) ) {
                                %>
                           <div>  
                           <a class="" href="/InstaDom/Profile">
                           <button type = "submit" class = "btn btn-primary">Edit Info</button>
                           </a>
                           </div> 
                           <%
                             }
                        }
                            %>                 
                            
             
              
             <div class="align-names">
             <h3 class=""><%= profile.getFirstname()%></h3>
             </div>
             <div class="align-names">
             <h3 class=""><%= profile.getLastname()%></h3>
             </div>
             
             <div class="align-names name-font">
             <h4><%= profile.getBio()%></h4>
             </div>
             
                      
         </div>
             <br>
             <br>
             <br>
             
        
            
       <div class="col-md-8 text-center">
                    
                        <%
                            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");

                            if (lsPics == null) {
                        %>
                        <h1 class="text-center">No Pics found... :(</h1>
                        <%
                        } else {
                            Iterator<Pic> iterator;
                            iterator = lsPics.iterator();
                            while (iterator.hasNext()) {
                                Pic p = (Pic) iterator.next();
                        %>
                        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a>
                        <p>Date: <%=p.getDate()%></p>
                        <a class="login-title" href="/Instagrim/Images/<%=p.getFollower()%>">Uploaded by <%=p.getFollower()%></a>

                        <%}
                            }%>
        
        <footer>
            
        </footer>
       
   
       
       </div> 
                            
       </div>
    </body>
</html>

