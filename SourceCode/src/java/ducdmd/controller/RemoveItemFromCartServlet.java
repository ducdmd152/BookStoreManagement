/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.cart.CartObject;
import ducdmd.utils.MyApplicationConstants;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MSI
 */
public class RemoveItemFromCartServlet extends HttpServlet {
//    private final String ERROR_PAGE = "error.html";
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
        
        String url = MyApplicationConstants.ApplicationScope.VIEW_CART_ACTION; 
        
        try {
            //1. Cust goes to his/her cart placement
            HttpSession session = request.getSession(false); // false for check scope timeout because user's problem
            if (session != null) {                
                //2. Cust takes his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if(cart != null) {                    
                    //3. Cust gets all items
                    Map<String, Integer> items = cart.getItems();
                    if(items != null) {                        
                        //4. Cust get all selected removed items
                        String[] selectedItems = request.getParameterValues("chkItem");
                        if(selectedItems != null) {                            
                            //5. System removes item by item from cart
                            for(String item : selectedItems) {
                                cart.removeItemFromCart(item);
                            } // end remove selected items.
                            session.setAttribute("CART", cart);
                        } // end selectedItems has existed
                    }
                } // end cart has existed
            } // end session has existed
        }
        finally {
            //6. System refresh cart 
            /// call View Your Cart again by URL rewriting
//            String urlRewriting = "DispatchController"
//                    + "?btAction=" + "View Your Cart";                                             
            response.sendRedirect(url);
            
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
