<%-- 
    Document   : PasswordSelect
    Created on : 2017-jan-06, 00:36:36
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>Password reset</title>
    </head>
    <body>
        <div id="header"></div>
        <center>
            <div id="main">
                <h1>Password reset</h1>
                Input your new password two times and it will be reset and you will once again be able to login to JspRPG.
                <br>
                <br>
                <form action="PasswordResetHandler" method="GET">  
                    Input password
                    <br>
                    <input type="text" name="inputEmail"/>
                    <br>
                    <br>
                    Input password again
                    <br>
                    <input type="text" name="inputEmail"/>
                    <br>
                    <br>
                    <input type="submit" name="passResetBut" value="Reset"/>
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
