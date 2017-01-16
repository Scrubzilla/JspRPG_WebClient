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

/**
 *
 * @author Nicklas
 */
@WebServlet(name = "LoginHandler", urlPatterns = {"/LoginHandler"})
public class LoginHandler extends HttpServlet {

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
                
                int tempAccountLevel = TempDatabase.getInstance().getUserRole(username);
                String accountLevel = "Standard";
                
                if(tempAccountLevel == 2){
                    accountLevel = "Admin";
                }
                else if(tempAccountLevel == 1){
                    accountLevel = "Premium";
                }
                
                String isPremium = Boolean.toString(TempDatabase.getInstance().getIsPremium(username));
                String hasCharacter = Boolean.toString(TempDatabase.getInstance().accountHasCharacter(username));
                
                HttpSession session = request.getSession(true);
                session.setAttribute("username", username);
                session.setAttribute("accountLevel", accountLevel);
                session.setAttribute("isPremium", isPremium);
                session.setAttribute("hasCharacter", hasCharacter);
                
                if (TempDatabase.getInstance().login(username, password) == true) {
                    response.sendRedirect("./AccountManagement.jsp");
                } else {
                    String error = "That username/password combination does not exist, try again!";
                    response.sendRedirect("./Login.jsp?error=" + error);
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

}
