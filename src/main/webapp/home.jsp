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
    </head>

    <body>

        <%UserProfile profile = (UserProfile) request.getAttribute("UserProfile"); %>
        <%session.setAttribute("profileName", (profile.getUsername()));%>
        <%LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>

        <nav class="navbar">
            <ul>
                <div class="navbar-brand text-center col-md-4">
                    <%

                        if (lg == null) {

                    %>    
                    <a class="navbar-brand" href="/InstaDom">Instagrim</a>
                    <%                } else {
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
                        if (lg != null) {

                            if (lg.getlogedin()) {
                                String UserName = lg.getUsername();
                    %>

                    <a class="navbar-status" href="/InstaDom/Images/<%=lg.getUsername()%>"><%=UserName%></a>
                    <a class="navbar-status" href="/InstaDom/Home/<%=lg.getUsername()%>">Home</a> 
                    <a class="navbar-status" href="/InstaDom/Logout">Log Out</a>
                    <%}
                    } else {
                    %>
                    <a class="navbar-status" href="/InstaDom">Log In</a>
                    <%
                        }%>


                </div>
            </ul>
        </nav> 

        <div class="container">


            <div class="col-md-4 profile-box home-prof-fix">

                <%
                    if (profile.getUUID() == null) {
                %>
                <img src="/InstaDom/img/generic-avatar.png" class="profile-photo img-circle"/>    
                <%} else {%>  

                <img src="/InstaDom/Image/<%= profile.getUUID()%>" class="profile-photo img-circle"/>                



                <%
                    }
                %>

            </div>


            <div class="col-md-8 overflow-fix home-prof-fix">
                <div class="col-md-4 profile-box">
                    <div>
                        <h1 class="username-title"><%= profile.getUsername()%></h1>
                    </div>


                    <%
                        if (lg != null) {
                            if ((lg.getProfileMatch() == false) && (!(lg.getUsername().equals(profile.getUsername())))) {
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
                        if (lg != null) {
                            if ((lg.getProfileMatch() == true) && (!(lg.getUsername().equals(profile.getUsername())))) {
                                session.setAttribute("following", true); %>
                    <div> 
                        <form method="get"  id="Follow" action="${pageContext.request.contextPath}/Follow">
                            <button type="submit" class ="btn btn-primary">Follow</button> 
                        </form>  
                    </div> 

                    <%
                            }
                        }
                        if (lg != null) {
                            if (lg.getUsername().equals(profile.getUsername())) {
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


                    <div>

                        <div class="align-names">

                            <%
                                if (profile.getFirstname() != null) {

                            %>

                            <h3 class=""><%= profile.getFirstname()%></h3>

                            <%} else {%>

                            <h3 class=""></h3>

                            <%}%>

                        </div>
                        <div class="align-names">

                            <%

                                if (profile.getLastname() != null) {

                            %>

                            <h3 class=""><%= profile.getLastname()%></h3>

                            <%} else {%>

                            <h3 class=""></h3>

                            <%}%>
                        </div>
                    </div>
                </div>



                <div class="col-md-4 profile-box">
                    <h3>Upload a picture</h3>
                        
                    <form method="POST" class="form-group" enctype="multipart/form-data" action="Image">                      
                        File to upload: <input type="file" required="required" name="file">                       
                        
                        </br>
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
                       
                        </br>

                        <input type="submit" class ="btn btn-primary" value="Upload">
                    </form>
                </div>

            </div>            
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>





            <%
                LinkedList<Pic> lsPics = (LinkedList<Pic>) request.getAttribute("Pics");

                if (lsPics == null || lsPics.size() == 0) {
            %>           

            <h1 class="text-center">You Haven't Followed Anyone Yet  :-(</h1>
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();
            %>
            <div class="centre-div marg-fix col-md-8">
                <div class="darken-img dark-marg-fix centre-img-home">
                    <a href="/InstaDom/Image/<%=p.getSUUID()%>" ><img src="/InstaDom/Image/<%=p.getSUUID()%>"></a>
                </div>
               
                <div class="text-center">
                    <p>Date: <%=p.getDate()%></p>
                    Uploaded by <a class="com-text" href="/InstaDom/Images/<%=p.getFollower()%>"><%=p.getFollower()%></a>
                </div></br>
              <div>
                    <% if (p.getComments() != null) {
                            Iterator<ComStore> citerator;
                            citerator = p.getComments().iterator();
                            while (citerator.hasNext()) {
                                ComStore c = (ComStore) citerator.next();%>
                                <p> Comment by <a class="com-text" href="/InstaDom/Images/<%=c.getCommenter()%>"><%=c.getCommenter()%> </a>
                                    @ <%=c.getTime()%>
                                <div class="com-text"><%=c.getComment()%></div></p>
                    <% }
                        }%>
                

                <%if (lg != null) {%>
                <form method="POST" class="form-group" action="${pageContext.request.contextPath}/Comment">
                    <input type="textarea" name="comment" rows="1" class="form-control" cols="48" placeholder="Comment here...." required>
                    </br>
                    <input type="submit" class="btn btn-default" value="Comment" >                     
                    <input type="hidden" name="commenter" value="<%=lg.getUsername()%>">
                    <input type="hidden" name="username" value="<%=request.getAttribute("user")%>">
                    <input type="hidden" name="picid" value="<%=p.getSUUID()%>">     
                </form> 
                </div>  
            
                
                <%}%>
            </div></br>
                    <%}%>
           
            <%}
               
            %> 

        
        </div>
            
        <footer>

        </footer>






    </body>
</html>

