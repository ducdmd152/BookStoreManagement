/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.cart.CartObject;
import ducdmd.product.ProductObject;
import ducdmd.preparedOrder.PreparedOrderList;
import ducdmd.preparedOrder.PreparedOrder;
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
public class ConfirmCheckoutServlet extends HttpServlet {

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

//        String url = "DispatchController"
//                + "?btAction=" + "View Your Cart";
//        String url = MyApplicationConstants.ConfirmCheckOutFeature.VIEW_CART_ACTION;
        String url = siteMaps.getProperty(
                MyApplicationConstants.CheckOutFeature.CHECKOUT_FAIL_PAGE
            );
        boolean dispatching = true;

        try {
            //1. cust goes to his/her cart's placement
            HttpSession session = request.getSession(false);

            if (session != null) {
                //2. cust takes his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    //3. cust gets all items
                    Map<String, Integer> items = cart.getItems();

                    if (items != null) {
                        //4. cust get selected checkout items
                        String[] selectedItems = request.getParameterValues("checkOutItem");

                        if (selectedItems != null) {
                            //4.1 system checking & refresh to adapt runtime update
                            cart.refresh(); /// to fix problems like changed of quantity in database
                            List<ProductObject> products = cart.getSelectedProductsInCart(selectedItems);

                            if (products != null) {
                                //4.2 store a new prepared order in to PREPARED_ORDER_LIST of the session
                                PreparedOrderList preparedOrderList = (PreparedOrderList) session.getAttribute("PREPARED_ORDER_LIST");

                                if (preparedOrderList == null) {
                                    preparedOrderList = new PreparedOrderList();
                                }

                                PreparedOrder preparedOrder = new PreparedOrder(products);
                                preparedOrderList.addOrderQueueItem(preparedOrder);

                                session.setAttribute("PREPARED_ORDER_LIST", preparedOrderList);

                                //4.3 store cache ~ in att of req to move for show confirm checkout
                                request.setAttribute("PREPARED_ORDER", preparedOrder);

                                // 5. System forward to show inf of confirm checkout  
                                url = siteMaps.getProperty(
                                        MyApplicationConstants.ConfirmCheckOutFeature.CONFIRM_CHECKOUT_PAGE
                                );
                                dispatching = true;
                            }
                        } else {
                            url = MyApplicationConstants.ApplicationScope.VIEW_CART_ACTION;
                            dispatching = false;
                        }
                    }
                }
            }
        } catch (NamingException ex) {
            log("ConfirmCheckOutServlet _SQL: " + ex.getMessage());
        } catch (SQLException ex) {
            log("ConfirmCheckOutServlet _Naming: " + ex.getMessage());
        } finally {
            if (dispatching) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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
