<%-- 
    Document   : PasswordRetrieval
    Created on : 2016-dec-29, 19:30:31
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>Password Retrieval</title>
    </head>
    <body>
        <div id="header"></div>
        <center>
            <div id="main">
                <h1>Password retrieval</h1>
                Input your email and a mail with a reset link will be sent to you if the email is bound to an account!
                <br>
                <br>
                <form action="PasswordRetrievalHandler" method="GET">  
                    <input type="text" name="inputEmail"/>
                    <br>
                    <br>
                    <input type="submit" name="passRetrievalBut" value="Send"/>
                </form>
                <br>
                <br>
                <%
                    String serverResponse = request.getParameter("response");

                    if (serverResponse != null) {
                        out.println(serverResponse);
                    }
                %>
            </div>
        </center>
    </body>
</html>
