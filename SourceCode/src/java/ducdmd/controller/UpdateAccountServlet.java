/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.registration.RegistrationCreateError;
import ducdmd.registration.RegistrationDAO;
import ducdmd.utils.MyApplicationConstants;
import ducdmd.utils.SHA256;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MSI
 */
public class UpdateAccountServlet extends HttpServlet {
//    private String ERROR_PAGE = "error.html";

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

//        String url = ERROR_PAGE;
        String url = MyApplicationConstants.ApplicationScope.ERROR_PAGE;

        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        boolean role = request.getParameter("isAdmin") != null;
        String searchValue = request.getParameter("lastSearchValue");

        boolean errorFound = false;
        RegistrationCreateError errors = new RegistrationCreateError();

        try {
            // 0. Check user's errors
            password = password.trim();
            if (password.length() < 8 || password.length() > 20) { /// use regex for enhance UX
                errorFound = true;
                errors.setPasswordLengthError("Password is required input from 8 to 20 characters");
            }

            if (errorFound) {                                
                url = MyApplicationConstants.ApplicationScope.SEARCH_LASTNAME_ACTION
                            + "?txtSearchValue=" + searchValue
                            + "&errors=" + errors.getPasswordLengthError();
            } else {
                // 1. Call DAO
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = true;
                result = result && dao.updateAccountPassword(username, SHA256.getHash(password));
                result = result && dao.updateAccountRole(username, role);

                // 2. Refresh by call Search Function again using URL Rewriting
                if (result) {
//                url = "DispatchController"
//                        + "?btAction=Search"
//                        + "&txtSearchValue=" + searchValue;
                    url = MyApplicationConstants.ApplicationScope.SEARCH_LASTNAME_ACTION
                            + "?txtSearchValue=" + searchValue
                            + "&updated=" + username;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            log("UpdateAccountServlet _PASSWORD_HASHING: " + ex.getMessage());
        } catch (SQLException ex) {
            log("UpdateAccountServlet _SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateAccountServlet _Naming: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            // using for url rewriting
            /// 1. undercontrol --> duplicated name for parameters
            /// 2. reduce cost --> ending the request scope

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
