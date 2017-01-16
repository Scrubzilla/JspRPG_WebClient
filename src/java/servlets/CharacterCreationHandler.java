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
@WebServlet(name = "CharacterCreationHandler", urlPatterns = {"/CharacterCreationHandler"})
public class CharacterCreationHandler extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            if (request.getParameter("createCharacterBut") != null) {

                HttpSession session = request.getSession(true);
                String currentUser = (String) session.getAttribute("username");

                String name = request.getParameter("characterName");
                int str = Integer.parseInt(request.getParameter("strValue"));
                int dex = Integer.parseInt(request.getParameter("dexValue"));
                int vit = Integer.parseInt(request.getParameter("vitValue"));
                int inte = Integer.parseInt(request.getParameter("inteValue"));
                int wis = Integer.parseInt(request.getParameter("wisValue"));
                int cha = Integer.parseInt(request.getParameter("chaValue"));

                //String serverResponse = TempDatabase.getInstance().addCharacter(currentUser, name, str, dex, vit, inte, wis, cha);
                String serverResponse = addCharacter(currentUser, name, 0, str, dex, vit, inte, wis, cha);
                if (serverResponse.equals("Character was created successfully!")) {
                   // String hasCharacter = Boolean.toString(TempDatabase.getInstance().accountHasCharacter(currentUser));
                    String hasCharacter = Boolean.toString(checkCharacterFromUsername(currentUser));

                    session.setAttribute("hasCharacter", hasCharacter);
                    response.sendRedirect("./AccountManagement.jsp?response=" + serverResponse);
                } else {
                    response.sendRedirect("./CharacterCreation.jsp?response=" + serverResponse);
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

    private String addCharacter(java.lang.String username, java.lang.String name, int portrait, int str, int dex, int vit, int intell, int wis, int chr) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service.getApplicationWebServicePort();
        return port.addCharacter(username, name, portrait, str, dex, vit, intell, wis, chr);
    }

    private boolean checkCharacterFromUsername(java.lang.String username) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ApplicationWebService port = service.getApplicationWebServicePort();
        return port.checkCharacterFromUsername(username);
    }

}
