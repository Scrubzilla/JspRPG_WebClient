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
                $('#commandBut').click(function (event) {  
                    var gameCommand = $('#commandField').val();
                    
                    $.get('InGameHandler', {command: gameCommand}, function (responseText) {
                        var response = responseText;
                        
                        if(response.length < 10){
                            addtxt('mainGame', responseText);
                        }
                    });
                });
                $('#messageBut').click(function (event) {  
                    var chatMessage = $('#messageField').val();
                    
                    $.get('InGameHandler', {message: chatMessage}, function (responseText) {
                        var response = responseText;
                        
                        if(response.length < 10){
                            addtxt('chatBox', responseText);
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
                <br>
                <div id="gameLeft" style="float: left;">
                    <h3>Gamescreen</h3>
                    <textarea id="mainGame" rows="30" cols="83" readonly="readonly" style="margin-left: 5px; overflow:auto; resize:none"></textarea> 
                    <br>
                    <br>
                    <input type="text" name="commandField" size="74" placeholder="Input action"/>
                    <input type="button" id="commandBut" value="Send"/>
                </div>
                
                <div id="gameLeft" style="float: right;">
                    <h3>Chat</h3>
                    <textarea id="chatBox" rows="30" cols="35" readonly="readonly" style="margin-right: 2px; overflow:auto; resize:none"></textarea>
                    <br>
                    <br>
                    <input type="text" id="messageField" size="27" placeholder="Input message" />
                    <input type="button" id="messageBut" value="Chat" style="margin-right: 3px;"/>
                </div>
                
                <form action="InGameHandler" method="GET">
                    <input type="submit" id="backToAccountBut" name="backToAccountBut" value="Back to account management" style="margin-top:40px;"/>
                </form>
            </div>
        </center>
    </body>
</html>


