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
public class LoginServlet extends HttpServlet {
//    private final String INVALID_PAGE = "invalid.html";
//    private final String SEARCH_PAGE = "search.jsp";

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

//        String url = INVALID_PAGE;
        String url = siteMaps.getProperty(
                MyApplicationConstants.LoginFeature.LOGIN_ERROR_PAGE
            );
        boolean dispatching = true;

        String username = request.getParameter("txtUsername"); // check roi moi lay du lieu can thiet
        String password = request.getParameter("txtPassword");

        try {
            username = username.trim(); // trim() for both register & login
            password = password.trim(); // trim() for both register & login

            // 1. Check login
            /// call Model/DAO
            RegistrationDAO dao = new RegistrationDAO();
            RegistrationDTO result = dao.checkLogin(username, SHA256.getHash(password));

            // 2. process result
            if (result != null) {
                /// 2.1 Saving username:password for session tracking
                HttpSession session = request.getSession(true); /// need be existed
                session.setAttribute("USER", result);

                // 2.2 Re-Authorization for user
                Authorization authorization = new Authorization();
                List<Pair<String, String>> featureNavs = authorization.getFeatureNavs(result.isRole()); // unlogined --> null
                session.setAttribute("FEATURE_NAVS", featureNavs);

                /// 2.3 Send cookie (username:password)
                Cookie cookieForUsername = new Cookie("username", username);
                cookieForUsername.setMaxAge(5 * 60);
                Cookie cookieForPassword = new Cookie("password", password);
                cookieForPassword.setMaxAge(5 * 60);

                response.addCookie(cookieForUsername);
                response.addCookie(cookieForPassword);

                /// 2.3 Go to Welcome Page
                boolean isAdmin = result.isRole();
                if (isAdmin) {
                    url = MyApplicationConstants.ApplicationScope.ACCOUNT_ADMINISTRATION_ACTION;
                    dispatching = false;
                } // end of admin authorization
                else {
                    url = MyApplicationConstants.ApplicationScope.SHOPPING_ACTION;
                    dispatching = false;
                } // end of un-admin authorization 
            } // end of login successfully
            else {
                /// fail to login
                request.setAttribute("ERROR_MESSAGE", "Incorrect username or password");
            }
        } catch (NoSuchAlgorithmException ex) {
            log("LoginServlet _PASSWORD_HASHING: " + ex.getMessage());
        } catch (SQLException ex) {
            log("LoginServlet _SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet _Naming: " + ex.getMessage());
        }
        finally {
            if (dispatching) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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
