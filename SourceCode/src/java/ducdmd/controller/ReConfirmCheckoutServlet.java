/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.preparedOrder.PreparedOrder;
import ducdmd.preparedOrder.PreparedOrderCheckoutError;
import ducdmd.preparedOrder.PreparedOrderList;
import ducdmd.utils.MyApplicationConstants;
import java.io.IOException;
import java.util.Properties;
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
public class ReConfirmCheckoutServlet extends HttpServlet {

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
        
        String url = siteMaps.getProperty(MyApplicationConstants.ReConfirmCheckOutFeature.CHECKOUT_FAIL_PAGE);
        
        int preparedOrderId = (int) request.getAttribute("PREPARED_ORDER_ID");
        PreparedOrderCheckoutError preparedOrderCheckoutError = (PreparedOrderCheckoutError) request.getAttribute("CHECKOUT_ERROR");
        
        boolean systemErrorFound = false;
        PreparedOrder preparedOrder = null;
        
        try {
            // GO TO FIND coresponding preparedOrder
            // - If exist --> re-confirm for the preparedOrder
            // - If not --> fail to checkout
            
            //1. system go to order queue's placement
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. system take order queue
                PreparedOrderList preparedOrderList = (PreparedOrderList) session.getAttribute("PREPARED_ORDER_LIST");

                if (preparedOrderList != null) {
                    //3. system determine a prepared order of the cust
                    preparedOrder = preparedOrderList.gerOrderQueueItem(preparedOrderId);

                    if (preparedOrder != null) {
                        // FOUND
                    } // end of preparedOrder is found
                    else {
                        systemErrorFound = true;
                        preparedOrderCheckoutError.setPrepareOrderIsNotFound("Not found the prepared order in list.");
                    }
                } else {
                    systemErrorFound = true;
                    preparedOrderCheckoutError.setPrepareOrderIsNotFound("Not found prepared order list.");
                }

            } // end of preparedOrderList is exist
            else {
                systemErrorFound = true;
                preparedOrderCheckoutError.setPrepareOrderIsNotFound("Session is not exist");
            }

            if (systemErrorFound) {
                // url = checkOutFail.jsp --> inform to user for taking a new checkout                            
                request.setAttribute("CHECKOUT_ERROR", preparedOrderCheckoutError);
            }
            else {
                // re-confirm checkout --> go to confirmCheckout.jsp
                request.setAttribute("USER_ERROR", preparedOrderCheckoutError);
                request.setAttribute("PREPARED_ORDER", preparedOrder);
                url = siteMaps.getProperty(MyApplicationConstants.ConfirmCheckOutFeature.CONFIRM_CHECKOUT_PAGE);
            }
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
