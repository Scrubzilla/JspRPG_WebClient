<%-- 
    Document   : AccountCreation
    Created on : 2016-dec-29, 19:30:18
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="header"></div>
        <center>
            <div id="main">
            <h1>Account creation</h1>
            <form action="AccountCreationHandler" method="GET">  
                <br>
                Enter username: 
                <br>
                <input type="text" name="inputUsername">
                <br>
                <br>
                Enter password: 
                <br>
                <input type="password" name="inputPassword"/>
                <br>
                <br>
                Enter password again: 
                <br>
                <input type="password" name="inputPassword2"/>
                <br>
                <br>
                Enter email: 
                <br>
                <input type="text" name="inputEmail"/>
                <br>
                <br>
                Choose secret question: 
                <br>
                <select id="secretQuestion" name="secretQuestion">
                    <option value="favouriteColor">Favourite color is?</option>
                    <option value="favouriteAnimal">Favourite animal is?</option>
                    <option value="favouriteGame">Favourite game is?</option>
                </select>
                <br>
                <br>
                Enter your answer: 
                <br>
                <input type="password" name="inputAnswer"/>
                <br>
                <br>
                <%
                    String error = request.getParameter("error");
                    String serverResponse = request.getParameter("response");

                    if (error != null) {
                        out.println(error);
                    }else if(serverResponse != null){
                        out.println(serverResponse);
                    }
                %>
                <br>
                <br>
                <input type="submit" name="createButton" value="Create account"/>
            </form>
                </div>
        </center>
    </body>
</html>
