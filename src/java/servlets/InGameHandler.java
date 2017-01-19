/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gameclasses.BattleHandler;
import gameclasses.ZoneManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import ws.ApplicationWebService_Service;
import ws.BattleHandlerBeanService;
import ws.ZoneHandlerBeanService;

/**
 *
 * @author Nicklas
 */
@WebServlet(name = "InGameHandler", urlPatterns = {"/InGameHandler"})
public class InGameHandler extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/JspRPGApplicationServer/ApplicationWebService.wsdl")
    private ApplicationWebService_Service service_2;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/JspRPGApplicationServer/BattleHandlerBeanService.wsdl")
    private BattleHandlerBeanService service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ZoneHandlerBeanService/ZoneHandlerBean.wsdl")
    private ZoneHandlerBeanService service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            HttpSession session = request.getSession(true);
            String currentUser = (String) session.getAttribute("username");

            String characterName = (String) session.getAttribute("characterName");
            String location = (String) session.getAttribute("location");

            if (request.getParameter("backToAccountBut") != null) {
                response.sendRedirect("./AccountManagement.jsp");
            } else {

                String command = null;
                String message = null;

                command = request.getParameter("command");
                message = request.getParameter("message");

                if (command != null) {
                    String splittedCommand[] = command.split("\\s+");
                    for (int i = 0; i < splittedCommand.length; i++) {
                        System.out.println(splittedCommand[i]);
                    }

                    String gameResponse = performAction(splittedCommand, currentUser, location, request, characterName);

                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(gameResponse);
                } else if (message != null) {
                    sendMessage(message, characterName, location);
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(message);
                }
                System.out.println(command);
                System.out.println(message);

            }

        }
    }

    private void sendMessage(String message, String currentUser, String currentLocation) {
        ZoneManager zm = new ZoneManager();

        int currentZone = zm.nameToId(currentLocation);
        System.out.println("SEND MESSAGE CURRENT ZONE -------- " + currentZone);
        switch (currentZone) {
            case 1:
                addToZone1Chat(message, currentUser);
                break;
            case 2:
                addToZone2Chat(message, currentUser);
                break;
            case 3:
                addToZone3Chat(message, currentUser);
                break;
            default:
                break;
        }
    }

    private String performAction(String command[], String currentUser, String currentLocation, HttpServletRequest request, String characterName) {
        ZoneManager zm = new ZoneManager();

        HttpSession session = request.getSession(true);
        String inCombat = (String) session.getAttribute("inCombat");

        if (inCombat.equals("false")) {
            if (command[0].equalsIgnoreCase("Travel") && command[1].equalsIgnoreCase("to")) {
                String zoneName = "";
                for (int i = 2; i < command.length; i++) {
                    if (i == 2) {
                        zoneName = command[i];
                        System.out.println("Iteration: " + i + "  " + zoneName);
                    } else {

                        zoneName = zoneName + " " + command[i];
                        System.out.println("Iteration: " + i + "  " + zoneName);
                    }

                }
                if (zm.nameToId(zoneName) != -1) {
                    changeZone(characterName, Integer.toString(zm.nameToId(zoneName)), zoneName, request);
                    return "You have safely arrived to " + zoneName + "#" + zoneName;
                } else {
                    return "That zone does not exist!";
                }

            } else if (command[0].equalsIgnoreCase("Lookup") && command[1].equalsIgnoreCase("Map") && command.length == 2) {

                return lookUp("Map", currentLocation);

            } else if (command[0].equalsIgnoreCase("Lookup") && command[1].equalsIgnoreCase("Creatures") && command.length == 2) {

                return lookUp("Creatures", currentLocation);

            } else if (command[0].equalsIgnoreCase("Lookup") && command[1].equalsIgnoreCase("Spells") && command.length == 2) {

                return "This command can only be user when in combat!";

            } else if (command[0].equalsIgnoreCase("Initiate") && command[1].equalsIgnoreCase("on") && command[2].equalsIgnoreCase("area") && command.length == 4) {
                try {
                    if (Integer.parseInt(command[3]) > 0 && Integer.parseInt(command[3]) <= 3) {
                        String battleResult = "";
                        String temp = startingBattle(characterName, Integer.toString(zm.nameToId(currentLocation)), command[3]);

                        if (temp.contains("Seuccesfully loaded all")) {
                            session.setAttribute("inCombat", "true");
                            battleResult = "You charge forward to battle in area " + command[3] + "!";
                            battleResult = battleResult + "\n" + getPlayerStatus() + "\n";
                            battleResult = battleResult + "\n" + "Your targets are the following:" + getMonstersInCombat() + "\n\nProceed with your turn.";

                        }
                        return battleResult;

                    } else {
                        return "That is not a valid area!";
                    }
                } catch (NumberFormatException nfe) {
                    return "That is not a valid area number!";
                }
            }
        } else if (inCombat.equals("true")) {
            if (command[0].equalsIgnoreCase("Lookup") && command[1].equalsIgnoreCase("Spells") && command.length == 2) {
                return lookUp("Spells", currentLocation);
            } else if (command[0].equalsIgnoreCase("Attack") && command.length == 3) {
                try {
                    String battleResult = "";
                    List<String> targetList = getCreatureListName();
                    ArrayList<Integer> indexList = new ArrayList<>();
                    indexList.clear();

                    int targetCounter = 0;
                    boolean targetExists = false;

                    for (int i = 0; i < targetList.size(); i++) {
                        if (command[1].equals(targetList.get(i))) {
                            targetExists = true;
                            targetCounter++;
                            indexList.add(i);
                        }
                    }

                    if (targetExists == true && (Integer.parseInt(command[2])) <= targetCounter && (Integer.parseInt(command[2])) > 0) {
                        String battleCheck = attackCreature(Integer.toString(indexList.get(Integer.parseInt(command[2]) - 1)));

                        if (battleCheck.contains("All creatures defeated!")) {
                            battleResult = battleResult + "\n" + battleCheck + "\nYou are now out of combat.\n";
                            session.setAttribute("inCombat", "false");

                        } else {
                            battleResult = battleResult + "\n" + battleCheck + "\n";
                            battleResult = battleResult + "\nThis is the status of the creatures now: \n" + getBothCreatureNameAndHP() + "\n";

                            String creAtt = creaturesAttackPlayer();

                            if (creAtt.contains("The creatures killed you")) {
                                battleResult = battleResult + "\n" + creaturesAttackPlayer() + ".\nGit gud and try again!\n\nYou are now out of combat.\n";
                                session.setAttribute("inCombat", "false");
                            } else {
                                battleResult = battleResult + "\n" + creaturesAttackPlayer() + "\n";
                                battleResult = battleResult + "\n" + getPlayerStatus() + "\n";
                            }

                        }

                        return battleResult;
                    } else {
                        return "That is not a valid target!";
                    }
                } catch (NumberFormatException nfe) {
                    return "That is not a valid targer number!";
                }
            } else if (command[0].equalsIgnoreCase("Cast") && command[2].equals("on") && command.length == 5) {
                try {
                    List<String> spellBook = getspellsNameList();

                    if (spellBook.contains(command[1])) {
                        int spellPos = -1;

                        for (int i = 0; i < spellBook.size(); i++) {
                            if (command[1].equals(spellBook.get(i))) {
                                spellPos = i;
                                break;
                            }
                        }

                        String battleResult = "";
                        List<String> targetList = getCreatureListName();
                        ArrayList<Integer> indexList = new ArrayList<>();
                        indexList.clear();

                        int targetCounter = 0;
                        boolean targetExists = false;

                        for (int i = 0; i < targetList.size(); i++) {
                            if (command[3].equals(targetList.get(i))) {
                                targetExists = true;
                                targetCounter++;
                                indexList.add(i);
                            }
                        }

                        if (targetExists == true && (Integer.parseInt(command[4])) <= targetCounter && (Integer.parseInt(command[4])) > 0) {
                            String battleCheck = useSpellOnCreature(Integer.toString(spellPos), Integer.toString(indexList.get(Integer.parseInt(command[4]) - 1)));

                            if (battleCheck.contains("All creatures defeated!")) {
                                battleResult = battleResult + "\n" + battleCheck + "\nYou are now out of combat.\n";
                                session.setAttribute("inCombat", "false");

                            } else {
                                battleResult = battleResult + "\n" + battleCheck + "\n";
                                battleResult = battleResult + "\nThis is the status of the creatures now: \n" + getBothCreatureNameAndHP() + "\n";

                                String creAtt = creaturesAttackPlayer();

                                if (creAtt.contains("The creatures killed you")) {
                                    battleResult = battleResult + "\n" + creaturesAttackPlayer() + ".\nGit gud and try again!\n\nYou are now out of combat.\n";
                                    session.setAttribute("inCombat", "false");
                                } else {
                                    battleResult = battleResult + "\n" + creaturesAttackPlayer() + "\n";
                                    battleResult = battleResult + "\n" + getPlayerStatus() + "\n";
                                }
                            }

                            return battleResult;
                        } else {
                            return "That is not a valid target!";

                        }

                    } else {
                        return "That is not a valid spell!";
                    }
                } catch (NumberFormatException nfe) {
                    return "That is not a valid target number!";
                }
            } else {
                return "You are in combat, focus!";
            }
        }

        return "That's not a valid command!";
    }

    private void changeZone(String currentUser, String zoneId, String destination, HttpServletRequest request) {
        moveAccountFromZone(currentUser);
        addAccountToZone(currentUser, zoneId);
        changeCharLocationInDB(currentUser, zoneId);

        HttpSession session = request.getSession(true);
        session.setAttribute("location", destination);
    }

    private String lookUp(String id, String currentLocation) {
        ZoneManager zm = new ZoneManager();

        if (id.equals("Creatures")) {
            String returnMonsters = "This zone has three areas with monsters.\n";
            List<String> monsterList = getCreaturesBeforeBattle(Integer.toString(zm.nameToId(currentLocation)), "1");

            for (int i = 0; i < monsterList.size(); i++) {
                if (i == 0) {
                    returnMonsters = returnMonsters + "\nThese creatures lives in area 1:" + monsterList.get(i);
                } else {
                    returnMonsters = returnMonsters + "\n" + monsterList.get(i);
                }

            }

            monsterList = getCreaturesBeforeBattle(Integer.toString(zm.nameToId(currentLocation)), "2");

            for (int i = 0; i < monsterList.size(); i++) {
                if (i == 0) {
                    returnMonsters = returnMonsters + "\nThese creatures lives in area 2:" + monsterList.get(i);
                } else {
                    returnMonsters = returnMonsters + "\n" + monsterList.get(i);
                }
            }

            monsterList = getCreaturesBeforeBattle(Integer.toString(zm.nameToId(currentLocation)), "3");

            for (int i = 0; i < monsterList.size(); i++) {
                if (i == 0) {
                    returnMonsters = returnMonsters + "\nThese creatures lives in area 3:" + monsterList.get(i);
                } else {
                    returnMonsters = returnMonsters + "\n" + monsterList.get(i);
                }
            }

            return returnMonsters;
        } else if (id.equals("Spells")) {
            List<String> spells = getspellsNameList();
            String returnSpells = "";

            for (int i = 0; i < spells.size(); i++) {
                returnSpells = returnSpells + "\n" + spells.get(i);

            }

            return "You currently have the knowledge of these spells: " + returnSpells;
        } else if (id.equals("Map")) {
            return "The zones in the world are: \n" + zm.getAllZones() + "\n";
        }

        return "Error";
    }

    //----------------------------COMBAT METHODS------------------------------\\
    private String printMonsterHP() {
        List<Integer> monsterHps = getCreatureHPList();
        String returnMonstersHP = "";

        for (int i = 0; i < monsterHps.size(); i++) {
            returnMonstersHP = returnMonstersHP + "\n" + Integer.toString(monsterHps.get(i));

        }

        return returnMonstersHP;
    }

    private String getPlayerStatus() {
        return "You currently have " + getCharacterHP() + " HP and " + getCharacterMana() + " Mana.";
    }

    private String getMonstersInCombat() {
        String returnMonsters = "";
        List<String> monsterList = getCreatureListName();
        for (int i = 0; i < monsterList.size(); i++) {
            returnMonsters = returnMonsters + "\n" + monsterList.get(i);

        }

        return returnMonsters;
    }

    private String getBothCreatureNameAndHP() {
        String returnNameAndHp = "";

        List<String> monsterList = getCreatureListName();
        List<Integer> monsterHps = getCreatureHPList();

        for (int i = 0; i < monsterList.size(); i++) {
            returnNameAndHp = returnNameAndHp + "\n" + monsterList.get(i) + ": " + monsterHps.get(i);

        }

        return returnNameAndHp;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

//----------------------------ZONE WEB SERVICE--------------------------------\\    
    //String accName
    private String moveAccountFromZone(java.lang.String arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.moveAccountFromZone(arg0);
    }

    //String accName, String zoneId
    private String addAccountToZone(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.addAccountToZone(arg0, arg1);
    }

    //String accName, String zoneId
    private String changeCharLocationInDB(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.changeCharLocationInDB(arg0, arg1);
    }

    private String addToZone1Chat(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.addToZone1Chat(arg0, arg1);
    }

    private String addToZone2Chat(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.addToZone2Chat(arg0, arg1);
    }

    private String addToZone3Chat(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.addToZone3Chat(arg0, arg1);
    }

    private String getCharacterName(java.lang.String username) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service_2.getApplicationWebServicePort();
        return port.getCharacterName(username);
    }

    private String getCharLocation(java.lang.String username) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service_2.getApplicationWebServicePort();
        return port.getCharLocation(username);
    }

    private java.util.List<java.lang.String> getCreaturesBeforeBattle(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getCreaturesBeforeBattle(arg0, arg1);
    }

//-------------------------------WEB SERVICE FOR BATTLE-----------------------\\
    private String startingBattle(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.startingBattle(arg0, arg1, arg2);
    }

    private String getCharacterHP() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getCharacterHP();
    }

    private String getCharacterMana() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getCharacterMana();
    }

    private java.util.List<java.lang.Integer> getCreatureHPList() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getCreatureHPList();
    }

    private String attackCreature(java.lang.String arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.attackCreature(arg0);
    }

    private String creaturesAttackPlayer() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.creaturesAttackPlayer();
    }

    private java.util.List<java.lang.String> getspellsNameList() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getspellsNameList();
    }

    private String useSpellOnCreature(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.useSpellOnCreature(arg0, arg1);
    }

    private java.util.List<java.lang.String> getCreatureListName() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getCreatureListName();
    }

}
