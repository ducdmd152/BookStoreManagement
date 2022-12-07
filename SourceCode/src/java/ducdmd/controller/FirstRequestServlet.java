/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.authorization.Authorization;
import ducdmd.registration.RegistrationDAO;
import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import ducdmd.utils.SHA256;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javafx.util.Pair;
import javax.naming.NamingException;
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
public class FirstRequestServlet extends HttpServlet {

//    private final String LOGIN_PAGE = "login.html";
//    private final String SEARCH_PAGE = "search.jsp";
//    private final String ACCOUNT_ADMINISTRATION_ACTION = "DispatchController"
//            + "?btAction=" + "Account Administration";
//    private final String SHOPPING_ACTION = "DispatchController"
//            + "?btAction=" + "Shopping";

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
        
        
        String url = siteMaps.getProperty(MyApplicationConstants.ApplicationScope.LOGIN_PAGE);
        boolean dispatching = true;

        try {
            // Check logined-user exist in session ~ for case user in a session + url without any parameter + cookie timeout
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("USER") == null) { // ~ if NOT: session already has user => try to AUTO-LOGIN with USER
                // 1. get candidate username:password pairs            
                /// by cookies
                Cookie[] cookies = request.getCookies();
                String cookieUsername = null;
                String cookiePassword = null;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) {
                            cookieUsername = cookie.getValue();
                        }

                        if (cookie.getName().equals("password")) {
                            cookiePassword = cookie.getValue();
                        }
                    }
                } // end cookies has existed

                // 2. Check valid username:password
                RegistrationDAO dao = new RegistrationDAO();

                RegistrationDTO result = null;
                String username = null;
                String password = null;

                /// Check cookie's pair  
                if (cookieUsername != null && cookiePassword != null) {                    
                    result = dao.checkLogin(cookieUsername, SHA256.getHash(cookiePassword));
                    if (result != null) {
                        username = cookieUsername;
                        password = cookiePassword;
                    }
                }

                /// 2.2 Process result
                Boolean role = null;
                if (result != null) {

                    /// 2.2.1 Saving username:password for session tracking
                    session = request.getSession(true); /// need be existed
                    session.setAttribute("USER", result);
                } // end of auto login successfully
                else {

                } // end of auto login fail
            }

            Boolean role = null; // un-logined

            session = request.getSession(); // session is existed surely
            RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
            
            if (user != null) { // logined
                boolean isAdmin = user.isRole();

                if (isAdmin) {
                    role = true; // admin's acc
                    url = MyApplicationConstants.ApplicationScope.ACCOUNT_ADMINISTRATION_ACTION;
                    dispatching = false;
                } // end of admin
                else {
                    role = false; // cust's acc
                    url = MyApplicationConstants.ApplicationScope.SHOPPING_ACTION;
                    dispatching = false;
                } // end of cust
            }

            /// 2.2.3 Authoriztion for user in session
            Authorization authorization = new Authorization();
            List<Pair<String, String>> featureNavs = authorization.getFeatureNavs(role); /// null for unlogined-user, true for admin, and false for cust
            session.setAttribute("FEATURE_NAVS", featureNavs);
            // end of already user acc exist in session        
    }
    catch (NoSuchAlgorithmException ex) {
            log("FirstRequestServlet _PASSWORD_HASHING: " + ex.getMessage());
    }
    catch (SQLException ex) {
            log("FirstRequestServlet _SQL: " + ex.getMessage());
    }
    catch (NamingException ex) {
            log("FirstRequestServlet _Naming: " + ex.getMessage());
    }
    finally {
        if(dispatching) { // che duong truyen file login.html
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
        else {

            response.sendRedirect(url);
        }

        /// Muon che duong truyen --> forward
        /// neu ko --> sendRedirect
//            response.sendRedirect(url);
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
