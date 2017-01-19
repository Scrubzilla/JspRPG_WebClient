<%-- 
    Document   : Login
    Created on : 2016-dec-22, 15:00:56
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>JspRPG Login</title>
    </head>
    <body>
        <div id="header"></div>
        <center>
            <div id="main">
                <h1>Login</h1>
                <%
                    String serverResponse = request.getParameter("response");

                    if (serverResponse != null) {
                        out.println(serverResponse);
                    }
                %>
                <form action="LoginHandler" method="GET">  
                    <br>
                    Username 
                    <br>
                    <input type="text" name="inputUsername">
                    <br>
                    <br>
                    Password
                    <br>
                    <input type="password" name="inputPassword"/>
                    <br>
                    <br>
                    <a href="./PasswordRetrieval.jsp">Did you forget your password?</a>
                    <br>
                    <a href="./AccountCreation.jsp">Create a new account!</a>
                    <br>
                    <br>
                    <input type="submit" name="loginButton" value="Login"/>
                </form>
            </div>
    </center>
</body>
</html>
