/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.cart.CartObject;
import ducdmd.product.ProductObject;
import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
public class ViewCartServlet extends HttpServlet {
//    private final String VIEW_CART_PAGE = "viewCart.jsp";
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
        
        String url = siteMaps.getProperty(MyApplicationConstants.ViewCartFeature.VIEW_CART_PAGE);
        
        try {
             // 0. Authorize (bussiness rule: admin's role is not allowed to use shopping features)
            HttpSession session = request.getSession(false);
            boolean isAdmin = false;
            if(session!=null) {
                RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
                if(user!=null) {
                    isAdmin = user.isRole();
                }
            }
            if(isAdmin) {
//                url = ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE;
                url = siteMaps.getProperty(MyApplicationConstants.ApplicationScope.ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE);
                return; /// report the error to user
            }
            
//            HttpSession session = request.getSession(false);
            //1. Cust goes to his/her cart placement
            if (session != null) {
                //2. Cust takes his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if(cart!=null) {                    
                    //3. Cust takes items
                    Map<String, Integer> items = cart.getItems();
                    
                    if (items != null) {
                        //4. System setup data to show the items in cart
                        ///4.1 get informations
                        cart.refresh(); /// to fix problems like changed of quantity in database
                        List<ProductObject> productsInCart = cart.getAllProductsInCart();
                        //4.2 set data to send for view
                        request.setAttribute("ITEMS_IN_CART", productsInCart);                        
                    }
                }
            }
        } catch (SQLException ex) {
            log("ViewCartServlet _SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("ViewCartServlet _Naming: " + ex.getMessage());
        }
        finally {
            // 5. System process to show            
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
