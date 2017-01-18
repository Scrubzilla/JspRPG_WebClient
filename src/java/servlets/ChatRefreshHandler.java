/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gameclasses.ZoneManager;
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
import ws.ZoneHandlerBeanService;

/**
 *
 * @author Nicklas
 */
@WebServlet(name = "ChatRefreshHandler", urlPatterns = {"/ChatRefreshHandler"})
public class ChatRefreshHandler extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/JspRPGApplicationServer/ApplicationWebService.wsdl")
    private ApplicationWebService_Service service_1;

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession(true);
            
            ZoneManager zm = new ZoneManager();
            int currentZone = zm.nameToId((String) session.getAttribute("location"));
            System.out.println(currentZone);
            List<String> chatMessages = null;
            System.out.println("Getting chat from zone...");
            
            switch (currentZone) {
                case 1:
                    System.out.println("Reading zone 1 chat");
                    chatMessages = getzone1Chat();
                    break;
                case 2:
                    System.out.println("Reading zone 2 chat");
                    chatMessages = getzone2Chat();
                    break;
                case 3:
                    System.out.println("Reading zone 3 chat");
                    chatMessages = getzone3Chat();
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
            
            System.out.println("Complete! Assembling string...");
            String chatString = "";
            if(chatMessages != null){
                for(int i = 0; i < chatMessages.size(); i++){
                    if(i == 0){
                        chatString = chatMessages.get(i);
                    }else{
                        chatString = chatString + "\n" + chatMessages.get(i);
                    }
                }
            }
            
            System.out.println("Returning string...");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(chatString);
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

    private java.util.List<java.lang.String> getzone1Chat() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.getzone1Chat();
    }

    private java.util.List<java.lang.String> getzone2Chat() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.getzone2Chat();
    }

    private java.util.List<java.lang.String> getzone3Chat() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.ZoneHandlerBean port = service.getZoneHandlerBeanPort();
        return port.getzone3Chat();
    }

}
