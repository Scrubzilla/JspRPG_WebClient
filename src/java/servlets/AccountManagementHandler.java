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
@WebServlet(name = "AccountManagementHandler", urlPatterns = {"/AccountManagementHandler"})
public class AccountManagementHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String serverResponse = "";
            HttpSession session = request.getSession(true);
            String currentUser = (String) session.getAttribute("username");
            System.out.println(currentUser);

            if (request.getParameter("playGameBut") != null) {
                response.sendRedirect("./InGame.jsp");
            }
            else if (request.getParameter("createCharacterBut") != null) {
                response.sendRedirect("./CharacterCreation.jsp");
            }
            else if (request.getParameter("seePremiumBut") != null) {
                
            }
            else if (request.getParameter("becomePremiumBut") != null) {
                            
            }
            else if (request.getParameter("logoutBut") != null) {
                response.sendRedirect("./Login.jsp");
            }
            else if (request.getParameter("submitPasswordBut") != null) {
                String oldPassword = request.getParameter("inputOldPassword");
                String newPassword = request.getParameter("inputNewPassword");
                String newPassword2 = request.getParameter("inputNewPassword2");

                if (!newPassword.equals(newPassword2)) {
                    serverResponse = "The new passwords does not match, try again!";
                } else {
                    serverResponse = TempDatabase.getInstance().changePassword(currentUser, oldPassword, newPassword);
                }

                response.sendRedirect("./AccountManagement.jsp?response=" + serverResponse);
            } else if (request.getParameter("submitEmailBut") != null) {
                String oldEmail = request.getParameter("inputOldEmail");
                String newEmail = request.getParameter("inputNewEmail");
                String newEmail2 = request.getParameter("inputNewEmail2");
                String sqAnswer = request.getParameter("inputSqAnswer");

                if (!newEmail.equals(newEmail2)) {
                    serverResponse = "The new emails does not match, try again!";
                }else{
                    serverResponse = TempDatabase.getInstance().changeEmail(currentUser, oldEmail, newEmail, sqAnswer);
                }

                response.sendRedirect("./AccountManagement.jsp?response=" + serverResponse);

            } else {
                String secretQuestion = TempDatabase.getInstance().getSecretQuestion(currentUser);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(secretQuestion);
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
