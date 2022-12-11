/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.registration.RegistrationDAO;
import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
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
public class SearchLastnameServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.jsp";
//    private final String SEARCH_RESULT_PAGE = "search.jsp";
//    private String ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE = "roleFeatureAuthorizationError.jsp";
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
        
//        String url = SEARCH_PAGE;
        String url = siteMaps.getProperty(MyApplicationConstants.SearchLastnameFeature.SEARCH_PAGE);
        
        String searchValue = request.getParameter("txtSearchValue");
        
        try {
            // 0. Check cust/guest try to use the feature
            HttpSession session = request.getSession(false);
            boolean isAdmin = false;
            if(session!=null) {
                RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
                if(user!=null) {
                    isAdmin = user.isRole();
                }
            }
            if(isAdmin==false) {
//                url = ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE;
                url = siteMaps.getProperty(MyApplicationConstants.ApplicationScope.ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE);
                return; /// report the error to user
            }
            
            // 1. Check valid search's Value
            if(searchValue.trim().length() > 0) {
                /// 2. Call DAO
                RegistrationDAO dao = new RegistrationDAO();
                dao.searchLastName(searchValue);
                /// 3. process result
                List<RegistrationDTO> result = dao.getAccountList();
                request.setAttribute("SEARCH_RESULT", result);
                
//                url = SEARCH_RESULT_PAGE;
                url = siteMaps.getProperty(MyApplicationConstants.SearchLastnameFeature.SEARCH_RESULT_PAGE);
            } // end search value has valid value
        } catch (SQLException ex) {
            log("SearchLastnameServlet _SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchLastnameServlet _Naming: " + ex.getMessage());
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
