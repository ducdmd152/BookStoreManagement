/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.registration.RegistrationCreateError;
import ducdmd.registration.RegistrationDAO;
import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import ducdmd.utils.SHA256;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MSI
 */
public class RegisterServlet extends HttpServlet {
//    private final String ERROR_PAGE = "register.jsp";
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
        
//        String url = ERROR_PAGE;
        String url = siteMaps.getProperty(MyApplicationConstants.RegisterFeature.ERROR_PAGE);
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        boolean errorFound = false;
        RegistrationCreateError errors = new RegistrationCreateError();
        
        try {           
            username = username.trim(); // trim() for both register & login
            password = password.trim(); // trim() for both register & login
            fullname = fullname.trim();
            confirm = confirm.trim();
            
            //1. Check user's errors
            if (username.length() < 6 || username.length() > 20) {
                errorFound = true;
                errors.setUsernameLengthError("Username is required input from 6 to 20 characters");
            }
            
            if (password.length() < 8 || password.length() > 20) { /// use regex for enhance UX
                errorFound = true;
                errors.setPasswordLengthError("Password is required input from 8 to 20 characters");
            } 
            else if (confirm.equals(password)==false) {
                errorFound = true;
                errors.setConfirmNotMatched("Confirm must be matched password");
            } //check confirm khi password is valied
            
            if (fullname.length()<2 || fullname.length()>40) {
                errorFound = true;
                errors.setFullNameLengthError("Full name is required input from 2 to 40 characters");
            }
            
            
            if(errorFound) {
                // 1.1 cache store
                request.setAttribute("CREATE_ERRORS", errors);
                // 1.2 show errors to user            
            }
            else {                
                //2. insert to DB
                RegistrationDAO dao = new RegistrationDAO();                
                RegistrationDTO dto = new RegistrationDTO(
                        username,
                        SHA256.getHash(password),
                        fullname,
                        false);
                
                boolean result = dao.createAccount(dto);
                if(result) {
//                    url = LOGIN_PAGE;
                    url = siteMaps.getProperty(MyApplicationConstants.ApplicationScope.LOGIN_PAGE);

                } // create is success
            }
                //3. Check system's errors
        } catch (NoSuchAlgorithmException ex) {
            log("CreateAccountServlet _PASSWORD_HASHING: " + ex.getMessage());
        } catch (SQLException ex) {
            // loi system: neu khong co kinh nghiem, cu lam di roi bat loi
            String errorMsg = ex.getMessage();
            log("CreateAccountServlet _SQL: " + errorMsg);
            
            if(errorMsg.contains("duplicate")) {
                errors.setUsernameIsExisted(username + " is existed username");
            }
            
            request.setAttribute("CREATE_ERRORS", errors);
        } catch (NamingException ex) {
            log("CreateAccountServlet _Naming: " + ex.getMessage());
        }
        finally {
            //4. transfer specified page
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
