<%-- 
    Document   : InGame
    Created on : 2016-dec-22, 15:02:40
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>JspRPG</title>
        <script>
            function addtxt(input, responseText) {
                var obj = document.getElementById(input);
                obj.value += responseText + "\n";
                


            }
        </script>

        <script src="http://code.jquery.com/jquery-latest.js"></script>

        <script>
            $(document).ready(function () {                        
                $('#submit').click(function (event) {  
                    var username = $('#user').val();
                    $.get('InGameHandler', {user: username}, function (responseText) {
                        var response = responseText;
                        alert(response.length);
                        
                        if(response.length < 10){
                            addtxt('mainGame', responseText);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <div id="header"></div>
        <center>
            <div id="main">
            
                <textarea id="mainGame" rows="30" cols="70" readonly="readonly" style="float: left;">At w3schools.com you will learn how to make a  </textarea> 
                <textarea id="chatBox" rows="30" cols="30" readonly="readonly" style="float: right; clear: none; ">At w3schools.com you will learn how to make a  </textarea><br>
                <br>
            <br>
            <br>
            <br>
            <input type="text" name="password" size="30" placeholder="Input action"/>
            <input type="text" id="user"/>
            <br>
            <br>
            <input type="button" id="submit" value="Ajax Submit"/>
            <br>
            <% 
                String userName = (String) session.getAttribute("username");
                out.println(userName);
            %>
            </div>
        </center>
    </body>
</html>


