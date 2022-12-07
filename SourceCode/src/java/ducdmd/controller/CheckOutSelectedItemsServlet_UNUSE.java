/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MSI
 */
public class CheckOutSelectedItemsServlet_UNUSE extends HttpServlet {

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
//        try {
//            //1. cust goes to his/her cart's placement
//            HttpSession session = request.getSession(false);
//            if (session != null) {
//                //2. cust takes his/her cart
//                CartObject cart = (CartObject) session.getAttribute("CART");
//                if (cart != null) {
//                    //3. cust gets all items
//                    Map<String, Integer> items = cart.getItems();
//
//                    if (items != null) {
//                        //4. cust get selected checkout items
//                        String[] selectedItems = request.getParameterValues("checkOutItem");
//
//                        if (selectedItems != null) {
//                            //4. system update item by item into order
//                            /// 4.1 create a new order
//                            OrdersDAO orderDAO = new OrdersDAO();
//                            int orderId = orderDAO.createNewOrder();
//                            float totalOfOrder = 0;
//                            
//                            ProductDAO productDAO = new ProductDAO();
//                            OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
//                            /// 4.2 insert item by item into orderDetails
//                            for (String sku : selectedItems) {
//                                
//                                // 4.2.1 get item's inf
//                                int quantity = items.get(sku);
//                                float price = 0;
//                                float total = 0;
//
//                                ProductDTO productDTO = productDAO.getProduct(sku);
//                                if (productDTO != null) {
//                                    price = productDTO.getPrice();
//                                    total = price * quantity;
//                                }
//                                
//                                totalOfOrder += total;
//
//                                // 4.2.2 insert into OrderDetails
//                                orderDetailsDAO.insertNewOrderDetail(orderId, sku, quantity, price, total);
//                                
//                                // 4.2.3 update information for the order
//                                orderDAO.updateOrderTotal(orderId, totalOfOrder);
//                                
//                                // 4.2.4 system remove item out from cart
//                                cart.removeItemFromCart(sku);
//                                session.setAttribute("CART", cart);
//                            }
//                        }
//                        //5. turn back to shopping
//                        String urlToShopping = "DispatchController"
//                                + "?btAction=" + "View Your Cart";
//                        response.sendRedirect(urlToShopping);
//                    }
//                }
//            }
//        } catch (NamingException ex) {
//            ex.printStackTrace();
//            RequestDispatcher rd = request.getRequestDispatcher("error.html");
//            rd.forward(request, response);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            RequestDispatcher rd = request.getRequestDispatcher("error.html");
//            rd.forward(request, response);
//        } finally {
//        }
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
