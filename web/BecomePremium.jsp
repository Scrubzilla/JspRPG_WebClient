<%-- 
    Document   : BecomePremium
    Created on : 2017-jan-18, 14:02:41
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>JspRPG Become Premium</title>
    </head>
    <body>
        <div id="header"></div>
    <center>
        <div id="main">
            <h1>Become a premium</h1>
            <form action="BecomePremiumHandler" method="GET">  
                <br>
                Input card number
                <br>
                <input type="text" name="inputCardNo">
                <br>
                <br>
                50kr will be drawn from your account and you will then be able to
                <br>
                enjoy premium member features.
                <br>
                <br>
                <input type="submit" name="submitBut" value="Submit"/>
            </form>
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
