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
        <title>JspRPG Login</title>
    </head>
    <body>
        <h1>Login</h1>

        <form action="LoginHandler" method="GET">  

            Enter username: <input type="text" name="username">
            <br>
            Enter password: <input type="text" name="password"/>
            <br>
            <br>
            <input type="submit" name="button" value="Login"/>
            <br>
            <br>

        </form>
    </body>
</html>
