<%-- 
    Document   : CharacterCreation
    Created on : 2017-jan-10, 02:50:21
    Author     : Nicklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="layout.css">
        <title>Character creation</title>
        <script type="text/javascript">
            var strength = 10;
            var dexterity = 10;
            var vitality = 10;
            var intelligence = 10;
            var wisdom = 10;
            var charisma = 10;
            var remainingPts = 15;

            function increase(id) {
                if (remainingPts > 0) {
                    if (id === "str") {
                        strength = strength + 1;
                        document.getElementById(id).innerHTML = strength;
                        remainingPts = remainingPts - 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("strValue").value = strength;
                    } else if (id === "dex") {
                        dexterity = dexterity + 1;
                        document.getElementById(id).innerHTML = dexterity;
                        remainingPts = remainingPts - 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("dexValue").value = dexterity;
                    } else if (id === "vit") {
                        vitality = vitality + 1;
                        document.getElementById(id).innerHTML = vitality;
                        remainingPts = remainingPts - 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("vitValue").value = vitality;
                    } else if (id === "int") {
                        intelligence = intelligence + 1;
                        document.getElementById(id).innerHTML = intelligence;
                        remainingPts = remainingPts - 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("inteValue").value = intelligence;
                    } else if (id === "wis") {
                        wisdom = wisdom + 1;
                        document.getElementById(id).innerHTML = wisdom;
                        remainingPts = remainingPts - 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("wisValue").value = wisdom;
                    } else if (id === "cha") {
                        charisma = charisma + 1;
                        document.getElementById(id).innerHTML = charisma;
                        remainingPts = remainingPts - 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("chaValue").value = charisma;
                    }
                }
            }

            function decrease(id) {
                if (id === "str") {
                    if (strength > 1) {
                        strength = strength - 1;
                        document.getElementById(id).innerHTML = strength;
                        remainingPts = remainingPts + 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("strValue").value = strength;
                    }
                } else if (id === "dex") {
                    if (dexterity > 1) {
                        dexterity = dexterity - 1;
                        document.getElementById(id).innerHTML = dexterity;
                        remainingPts = remainingPts + 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("dexValue").value = dexterity;
                    }
                } else if (id === "vit") {
                    if (vitality > 1) {
                        vitality = vitality - 1;
                        document.getElementById(id).innerHTML = vitality;
                        remainingPts = remainingPts + 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("vitValue").value = vitality;
                    }
                } else if (id === "int") {
                    if (intelligence > 1) {
                        intelligence = intelligence - 1;
                        document.getElementById(id).innerHTML = intelligence;
                        remainingPts = remainingPts + 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("inteValue").value = intelligence;
                    }
                } else if (id === "wis") {
                    if (wisdom > 1) {
                        wisdom = wisdom - 1;
                        document.getElementById(id).innerHTML = wisdom;
                        remainingPts = remainingPts + 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("wisValue").value = wisdom;
                    }
                } else if (id === "cha") {
                    if (charisma > 1) {
                        charisma = charisma - 1;
                        document.getElementById(id).innerHTML = charisma;
                        remainingPts = remainingPts + 1;
                        document.getElementById("remainingPts").innerHTML = remainingPts;
                        document.getElementById("chaValue").value = charisma;
                    }
                }
            }
        </script>
    </head>
    <body>
        <div id="header"></div>
    <center>
        <div id="main">
            <h1>Create a character:</h1>
            <form action="CharacterCreationHandler" method="GET">  
                <br>
                Enter character name: 
                <br>
                <input type="text" name="characterName">
                <br>
                <br>
                <u>Stat points:</u>
                <br>
                <br>
                Remaining points: <p id ="remainingPts" style="display:inline">15</p>
                <br>
                <br>
                Strength:
                <input type="button" value="-" style="margin: 0px 10px 0px 60px; height:20px; width:30px;" onclick="decrease('str')">
                <p id ="str" style="display:inline">10</p>
                <input type="button" value="+"style="margin-left: 10px; height:20px; width:30px;" onclick="increase('str')">
                <br>
                <br>
                Dexterity:
                <input type="button" value="-" style="margin: 0px 10px 0px 55px; height:20px; width:30px;" onclick="decrease('dex')">
                <p id ="dex" style="display:inline">10</p>
                <input type="button" value="+"style="margin-left: 10px; height:20px; width:30px;" onclick="increase('dex')">
                <br>
                <br>
                Vitality:
                <input type="button" value="-" style="margin: 0px 10px 0px 67px; height:20px; width:30px;" onclick="decrease('vit')">
                <p id ="vit" style="display:inline">10</p>
                <input type="button" value="+"style="margin-left: 10px; height:20px; width:30px;" onclick="increase('vit')">
                <br>
                <br>
                Intelligence:
                <input type="button" value="-" style="margin: 0px 10px 0px 40px; height:20px; width:30px;" onclick="decrease('int')">
                <p id ="int" style="display:inline">10</p>
                <input type="button" value="+"style="margin-left: 10px; height:20px; width:30px;" onclick="increase('int')">
                <br>
                <br>
                Wisdom:
                <input type="button" value="-" style="margin: 0px 10px 0px 60px; height:20px; width:30px;" onclick="decrease('wis')">
                <p id ="wis" style="display:inline">10</p>
                <input type="button" value="+"style="margin-left: 10px; height:20px; width:30px;" onclick="increase('wis')">
                <br>
                <br>
                Charisma:
                <input type="button" value="-" style="margin: 0px 10px 0px 54px; height:20px; width:30px;" onclick="decrease('cha')">
                <p id ="cha" style="display:inline">10</p>
                <input type="button" value="+"style="margin-left: 10px; height:20px; width:30px;" onclick="increase('cha')">
                <br>
                <br>
                <br>
                <br>
                <input type="hidden" id="strValue" name="strValue" value ="10"/>
                <input type="hidden" id="dexValue" name="dexValue" value ="10"/>
                <input type="hidden" id="vitValue" name="vitValue" value ="10"/>
                <input type="hidden" id="inteValue" name="inteValue" value ="10"/>
                <input type="hidden" id="wisValue" name="wisValue" value ="10"/>
                <input type="hidden" id="chaValue" name="chaValue" value ="10"/>
                <input type="submit" name="createCharacterBut" value="Create character"/>
                <br>
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
