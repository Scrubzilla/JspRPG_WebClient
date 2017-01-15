<%-- 
    Document   : AccountManagement
    Created on : 2016-dec-22, 15:02:00
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>JspRPG Account Management</title>
    </head>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function () {                        
            $("#resetPasswordBut").click(function () {
                $("#resetPassword").show();
                $("#resetEmail").hide();
            });
            $("#resetEmailBut").click(function () {
                $.get('ChangeHandler', function (responseText) {
                    $('#secretQuestion').text(responseText); 
                });

                $("#resetEmail").show();
                $("#resetPassword").hide();
            });
        });
    </script>
    <body>
        <div id="header"></div>
        <center>
            <div id="main">
                <%
                    String currentUser = (String) session.getAttribute("username");
                    String userRole = (String) session.getAttribute("accountLevel");
                    String isPremium = (String) session.getAttribute("isPremium");
                    String hasCharacter = (String) session.getAttribute("hasCharacter");
                    out.println("Logged in as: " + currentUser);
                %>
                
                <form action="AccountManagementHandler" method="GET"> 
                    <br>
                    <br>
                    <% if (hasCharacter.equals("true")) { %> 
                    <input type="submit" name="playGameBut" value="Play!" style="height:80px; width:180px;"/>
                    <% } else {
                    %>
                    <input type="submit" name="createCharacterBut" value="Create character!" style="height:80px; width:180px;"/>
                    <% } %>

                    <% if (isPremium.equals("true")) { %> 
                    <input type="submit" name="seePremiumBut" value="See premium" style="height:80px;"/>
                    <% } else {
                    %>
                    <input type="submit" name="becomePremiumBut" value="Become premium!" style="height:80px;"/>
                    <% } %>
                    <br>
                    <br>
                    <input type="button" id="resetPasswordBut" name="changePasswordBut" value="Change password" style="height:20px; width:144px"/>
                    <input type="button" id="resetEmailBut" name="changeEmailBut" value="Change your email" style="height:20px; width:144px"/>

                    <div id="resetPassword" hidden>
                        <br>
                        Old password
                        <br>
                        <input type="text" name="inputOldPassword"/>
                        <br>
                        <br>
                        New password
                        <br>
                        <input type="text" name="inputNewPassword"/>
                        <br>
                        <br>
                        New password (again) 
                        <br>
                        <input type="text" name="inputNewPassword2"/>
                        <br>
                        <br>
                        <input type="submit" name="submitPasswordBut" value="Proceed"/>
                    </div>
                    <br>
                    <div id="resetEmail" hidden>
                        <br>
                        Old email
                        <br>
                        <input type="text" name="inputOldEmail"/>
                        <br>
                        <br>
                        New email 
                        <br>
                        <input type="text" name="inputNewEmail"/>
                        <br>
                        <br>
                        New email (again) 
                        <br>
                        <input type="text" name="inputNewEmail2"/>
                        <br>
                        <br>
                        <div id="secretQuestion"></div>
                        <br>
                        Your answer:
                        <br>
                        <input type="text" name="inputSqAnswer"/>
                        <br>
                        <br>
                        <input type="submit" name="submitEmailBut" value="Proceed"/>
                        <br>
                        <br>
                    </div>
                </form>
                    <br>
                <%
                    String serverResponse = request.getParameter("response");

                    if (serverResponse != null) {
                        out.println(serverResponse);
                    }
                %>
            </div>
        <center>
    </body>
</html>
