/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.cart.CartObject;
import ducdmd.orderDetails.OrderDetailsDAO;
import ducdmd.product.ProductDAO;
import ducdmd.product.ProductDTO;
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
public class ShoppingServlet extends HttpServlet {
//    private final String ERROR_PAGE = "error.html";
//    private final String SHOPPING_PAGE = "shopping.jsp";

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
        
        String url = siteMaps.getProperty(MyApplicationConstants.ShoppingFeature.SHOPPING_PAGE);

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
                url = siteMaps.getProperty(MyApplicationConstants.SearchLastnameFeature.ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE);
                return; /// report the error to user
            }
            
            // 1. Call DAO --> to get product list
            ProductDAO productDAO = new ProductDAO();
            productDAO.loadAvailableProducts();
            ///2. process result
            List<ProductDTO> dtos = productDAO.getProductList();
            
            // 2.1 Refix quantity of each dto --> rest quantity
            OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
            
            for(ProductDTO dto : dtos) {
                int orderedQuantity = orderDetailsDAO.getOrderedQuantityOf(dto.getSku());
                int restQuantity = dto.getQuantity() - orderedQuantity;
                
                dto.setQuantity(restQuantity);
            }
            
            /// 2.2 Re-fix quantity for specific user
//            HttpSession session = request.getSession(false);
            session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    cart.refresh(); // re-fix for proper with real quantity in DB
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        for (ProductDTO dto : dtos) {
                            String sku = dto.getSku();
                            if (items.containsKey(sku)) {
                                int quantityInCart = items.get(sku);

                                int restQuantiy = dto.getQuantity() - quantityInCart;
                                if (restQuantiy < 0) {
                                    restQuantiy = 0;
                                }

                                dto.setQuantity(restQuantiy);
                            }
                        }
                    } // end of items does exist
                } // end of cart does exist
            } // end of session does exist

            /// 2.2 store to cache for showing
            request.setAttribute("PRODUCT_LIST", dtos);            
        } catch (SQLException ex) {
            log("ShoppingServlet _SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("ShoppingServlet _Naming: " + ex.getMessage());
        } finally {            
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
