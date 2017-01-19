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

            function changeZoneName(location) {
                document.getElementById("chatName").innerHTML = "Chat - " + location;

            }

            function refrestChat(input, responseText) {
                var obj = document.getElementById(input);
                obj.value = responseText + "\n";
            }
        </script>

        <script src="http://code.jquery.com/jquery-latest.js"></script>

        <script>
            $(document).ready(function () {                        
                $('#commandBut').click(function (event) {  
                    var gameCommand = $('#commandField').val();
                    $('#commandField').val("");
                    $.get('InGameHandler', {command: gameCommand}, function (responseText) {
                        var response = responseText;
                        var res = responseText.split("#");
                        
                        addtxt('mainGame', res[0]);
                        var n = res[0].includes("You have safely arrived to");

                        if (n === true) {
                            var locationName = res[1];
                            changeZoneName(locationName);
                        }
                    });
                });
                $('#messageBut').click(function (event) {  
                    var chatMessage = $('#messageField').val();
                    $('#messageField').val("");
                    $.get('InGameHandler', {message: chatMessage}, function (responseText) {

                    });
                });
            });

            var auto = setInterval(function ()
            {
                $.get('ChatRefreshHandler', function (responseText) {
                    refrestChat('chatBox', responseText);

                });
            }, 1000);
        </script>
    </head>
    <body>
        <div id="header"></div>
    <center>
        <div id="main">
            <%
                String currentUser = (String) session.getAttribute("username");
                String userRole = (String) session.getAttribute("accountLevel");
                String characterName = (String) session.getAttribute("characterName");
                String location = (String) session.getAttribute("location");
                out.println("<B> Logged in as: </B>" + currentUser + "&nbsp &nbsp &nbsp &nbsp &nbsp<B>Character name: </B>" + characterName + "&nbsp &nbsp &nbsp &nbsp &nbsp<B> Account Status: </B>" + userRole);
            %>
            <br>
            <div id="gameLeft" style="float: left;">
                <h3>Gamescreen</h3>
                <textarea id="mainGame" rows="30" cols="83" readonly="readonly" style="margin-left: 5px; overflow:auto; resize:none"></textarea> 
                <br>
                <br>
                <input type="text" id="commandField" name="commandField" size="74" placeholder="Input action"/>
                <input type="button" id="commandBut" value="Send"/>
            </div>

            <div id="gameLeft" style="float: right;">
                <h3><p id ="chatName" style="display:inline">Chat - <%out.println(location);%></p></h3>

                <textarea id="chatBox" rows="30" cols="35" readonly="readonly" style="margin-right: 2px; overflow:auto; resize:none"></textarea>
                <br>
                <br>
                <input type="text" id="messageField" size="27" placeholder="Input message" />
                <input type="button" id="messageBut" value="Chat" style="margin-right: 3px;"/>
            </div>

            <form action="InGameHandler" method="GET">
                <input type="submit" id="backToAccountBut" name="backToAccountBut" value="Back to account management" style="margin-top:40px;"/>
                <br>
                <p style="margin-top: 30px;">
                    <u>Commands:</u>
                    <br>
                    Travel to zoneName - Will take you character to the specified zone.
                    <br>
                    <br>
                    Attack targetName x - You will attack the specified target with position x in the list.
                    <br>
                    <br>
                    Cast spellName on targetName - You will cast the specified spell on the specified target.
                    <br>
                    <br>
                    Lookup spells - You will see all of the spells that you know.
                    <br>
                    <br>
                    Lookup creatures - You will see all of the monsters in the zone.
                    <br>
                    <br>
                    Lookup Map - You will see all of the zone names.
                </p>
            </form>
        </div>
    </center>
</body>
</html>


