/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
            String characterName = getCharacterName(currentUser);
            
            if (request.getParameter("backToAccountBut") != null) {
                response.sendRedirect("./AccountManagement.jsp");
            } else {

                String command = null;
                String message = null;

                command = request.getParameter("command");
                message = request.getParameter("message");

                if (command != null) {
                    String splittedCommand[] = command.split("\\s+");
                    for(int i = 0; i < splittedCommand.length; i++){
                        System.out.println(splittedCommand[i]);
                    }
                    
                    String gameResponse = performAction(splittedCommand, currentUser);
                    
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(gameResponse);
                } else if (message != null) {
                    sendMessage(message, characterName);
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(message);
                }
                System.out.println(command);
                System.out.println(message);

            }

        }
    }

    private void sendMessage(String message, String currentUser) {
        int currentZone = 1; //REPLACE
        
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
    
    private String performAction(String command[], String currentUser) {
        if (command[0].equalsIgnoreCase("Travel") && command[1].equalsIgnoreCase("to") && command.length == 3) {
            changeZone(currentUser, command[2]);
            return "You have safely arrived to" + command[2];
        } else if (command[0].equalsIgnoreCase("Attack") && command.length == 3) {
            List<String> targetList = getCreatureListName(); //REPLACE THIS ASAP
            int targetCounter = 0;
            boolean targetExists = false;
            
            for(int i = 0; i < targetList.size(); i++){
                if(command[1].equals(targetList.get(i))){
                    targetExists = true;
                    targetCounter++;
                }
            }
            
            if(targetExists == true && (Integer.parseInt(command[2])+1) <= targetCounter){
                //startBattle();
                //Do battle stuff
            }else{
                return "That is not a valid target!";
            }
            
        } else if (command[0].equalsIgnoreCase("Cast")) {

        } else if (command[0].equalsIgnoreCase("Lookup")) {

        }

        return "That's not a valid command!";
    }

    private void changeZone(String currentUser, String zoneId) {
        System.out.println("1");
        moveAccountFromZone(currentUser);
        System.out.println("2");
        addAccountToZone(currentUser, zoneId);
        System.out.println("3");
        changeCharLocationInDB(currentUser, zoneId);
        System.out.println("4");
    }
    
    private void attackEnemy(){
    
    }

    private void castSpell(){
    
    }
    
    private void lookUp(){
    
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

    private java.util.List<java.lang.String> getCreatureListName() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BattleHandlerBean port = service_1.getBattleHandlerBeanPort();
        return port.getCreatureListName();
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

    
    

}



//Vill ha metod för att få character namn
//Vill ha metod för att se characterns nuvarande zone
//Vill kunna få en lista med enemies i zonen/areat
//Vill ha en metod för att få namnet på zonen kanske? :o
//Name of monster please