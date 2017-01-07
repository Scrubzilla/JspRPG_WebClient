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
                    $.get('GameHandler', {user: username}, function (responseText) {
                        var response = responseText;
                        alert(response.length);
                        
                        if(response.length < 10){
                            addtxt('gameActions', responseText);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <center>
            <textarea id="gameActions" rows="10" cols="100" readonly="readonly" >At w3schools.com you will learn how to make a website. We offer free tutorials in all web development technologies. </textarea>
            <br>
            <br>
            <br>
            <input type="text" name="password" size="98" placeholder="Input action"/>
            <input type="text" id="user"/>
            <input type="button" id="submit" value="Ajax Submit"/>
            <br>
            <% 
                String userName = (String) session.getAttribute("username");
                out.println(userName);
            %>
        </center>
    </body>
</html>


