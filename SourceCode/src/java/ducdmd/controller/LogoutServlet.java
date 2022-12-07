/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.authorization.Authorization;
import ducdmd.utils.MyApplicationConstants;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javafx.util.Pair;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MSI
 */
public class LogoutServlet extends HttpServlet {

//    private final String LOGIN_PAGE = "login.html";

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
        
        try {
            //1. Check session
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. Invalidate session
                session.invalidate();
            } // end of session exist
            
            // 3. Update cookies
            Cookie cookieForUsername = new Cookie("username", "");/// doesn't follow correct format of username --> fail when try to auto login
            cookieForUsername.setMaxAge(1); /// set 1s --> removing after client receive response
            Cookie cookieForPassword = new Cookie("password", "");
            cookieForPassword.setMaxAge(1);
            
            response.addCookie(cookieForUsername);
            response.addCookie(cookieForPassword);
            
            // 4. Re-Authorization for unlogined user            
            Authorization authorization = new Authorization();
            List<Pair<String,String>> featureNavs = authorization.getFeatureNavs(null); // unlogined --> null
            session = request.getSession(true);
            session.setAttribute("FEATURE_NAVS", featureNavs);
        }
        finally {
            //4. Move to login page
            String url = siteMaps.getProperty(MyApplicationConstants.LogoutFeature.LOGIN_PAGE);
//            response.sendRedirect(url);
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
