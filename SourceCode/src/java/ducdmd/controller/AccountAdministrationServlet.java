/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MSI
 */
public class AccountAdministrationServlet extends HttpServlet {
//    private String LOGIN_PAGE = "login.html";
//    private String ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE = "roleFeatureAuthorizationError.html";
//    private String SEARCH_PAGE = "search.jsp";
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
        
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
        
//        String url = LOGIN_PAGE;
        String url = siteMaps.getProperty(MyApplicationConstants.AccountAdministrationFeature.LOGIN_PAGE);
        
        try {
            /// 1. Check current role of user in session => authorization
            HttpSession session = request.getSession(false);
            if(session!=null) {
                RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
                if(user!=null) {
                    boolean isAdmin = user.isRole();
                    if(isAdmin) {
//                        url = SEARCH_PAGE;
                        url = siteMaps.getProperty(MyApplicationConstants.AccountAdministrationFeature.SEARCH_PAGE);
                    }
                    else {
//                        url = ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE;
                        url = siteMaps.getProperty(MyApplicationConstants.AccountAdministrationFeature.ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE);
                    }
                }
            }
        }
        finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
