/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.TempDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import ws.ApplicationWebService_Service;

/**
 *
 * @author Nicklas
 */
@WebServlet(name = "LoginHandler", urlPatterns = {"/LoginHandler"})
public class LoginHandler extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/JspRPGApplicationServer/ApplicationWebService.wsdl")
    private ApplicationWebService_Service service;

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

            if (request.getParameter("fakeAccount") != null) {
                TempDatabase.getInstance().addAccount("Deebian", "gulbil123", "test@hotmail.com", "Favourite color is?", "Blue", false);
                TempDatabase.getInstance().addAccount("Adminatus", "gulbil123", "test1@hotmail.com", "Favourite color is?", "Blue", true);

                TempDatabase.getInstance().addCharacter("Adminatus", "Herpules", 1, 1, 1, 1, 1, 1);
                TempDatabase.getInstance().addCharacter("Derpinator", "Armando", 1, 1, 1, 1, 1, 1);
            } else {
                String username = request.getParameter("inputUsername");
                String password = request.getParameter("inputPassword");

                if (!logInCredentials(username, password).equals("0")) {

                    String accountLevel = getRoleFromAccount(username);
                    String isPremium = "false";
                    
                    if(accountLevel.equals("2")){
                        accountLevel = "Admin";
                        isPremium = "true";
                    }else if(accountLevel.equals("1")){
                        accountLevel = "Premium";
                        isPremium = "true";
                    }else if(accountLevel.equals("0")){
                        accountLevel = "Standard";
                        isPremium = "false";
                    }

                    String hasCharacter = Boolean.toString(checkCharacterFromUsername(username));

                    HttpSession session = request.getSession(true);
                    session.setAttribute("username", username);
                    session.setAttribute("accountLevel", accountLevel);
                    session.setAttribute("isPremium", isPremium);
                    session.setAttribute("hasCharacter", hasCharacter);
                    
                    response.sendRedirect("./AccountManagement.jsp");
                } else {
                    String serverResponse = "That username/password combination does not exist, try again!";
                    response.sendRedirect("./Login.jsp?response=" + serverResponse);
                }
            }
        }
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

    private String logInCredentials(java.lang.String username, java.lang.String password) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service.getApplicationWebServicePort();
        return port.logInCredentials(username, password);
    }

    private String getRoleFromAccount(java.lang.String username) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service.getApplicationWebServicePort();
        return port.getRoleFromAccount(username);
    }

    private boolean checkCharacterFromUsername(java.lang.String username) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service.getApplicationWebServicePort();
        return port.checkCharacterFromUsername(username);
    }
    
    

}
