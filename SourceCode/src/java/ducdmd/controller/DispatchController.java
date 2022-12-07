/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MSI
 */
@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {
//    private final String LOGIN_PAGE = "login.html";
//    private final String LOGIN_CONTROLLER = "LoginServlet";
//    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastnameServlet";
//    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
//    private final String FIRST_REQUEST_CONTROLLER = "FirstRequestServlet";
//    private final String UPDATE_ACCOUNT_CONTROLLER = "UpdateAccountServlet";
//    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet";
//    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
//    private final String SHOPPING_CONTROLLER = "ShoppingServlet";
//    private final String REMOVE_ITEM_FROM_CART_CONTROLLER = "RemoveItemFromCartServlet";
//    private final String CHECK_OUT_CONTROLLER = "CheckOutServlet";
//    private final String LOGOUT_CONTROLLER = "LogoutServlet";
//    private final String REGISTER_CONTROLLER = "RegisterServlet";
//    private final String CONFIRM_CHECKOUT_CONTROLLER = "CheckOutSelectedItemsServlet";
//    private final String ACCOUNT_ADMINISTRATION_CONTROLLER = "AccountAdministrationServlet";
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
        
//        ServletContext context = this.getServletContext();
//        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
//        
////        String url = LOGIN_PAGE;
//        String url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_PAGE);
//        
//        // Which button did user click?
//        String button = request.getParameter("btAction"); /// copy tu here to HTML form
//        System.out.println("Welcome to DispatchController");
//        
//        try {
//            if(button == null) {
//                // first req
////                url = FIRST_REQUEST_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.FIRST_REQUEST_CONTROLLER);
//            }
//            else if (button.equals("Login")) {
////                url = LOGIN_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_CONTROLLER);
//            }
//            else if (button.equals("Search")) {
////                url = SEARCH_LASTNAME_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.SEARCH_LASTNAME_CONTROLLER);
//            }
//            else if (button.equals("delete")) {
////                url = DELETE_ACCOUNT_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.DELETE_ACCOUNT_CONTROLLER);
//            }
//            else if (button.equals("Update")) {
////                url = UPDATE_ACCOUNT_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.UPDATE_ACCOUNT_CONTROLLER);
//            }
//            else if (button.equals("Add Book to Your Cart")) {
////                url = ADD_TO_CART_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.ADD_TO_CART_CONTROLLER);
//            }
//            else if (button.equals("View Your Cart")) {
////                url = VIEW_CART_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.VIEW_CART_CONTROLLER);
//            }
//            else if (button.equals("Shopping")) {
////                url = SHOPPING_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.SHOPPING_CONTROLLER);
//            }
//            else if (button.equals("Remove Selected Items")) {
////                url = REMOVE_ITEM_FROM_CART_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.REMOVE_ITEM_FROM_CART_CONTROLLER);
//            }
//            else if (button.equals("Checkout")) {
////                url = CHECK_OUT_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.CHECK_OUT_CONTROLLER);
//            }
//            else if (button.equals("Log out")) {
////                url = LOGOUT_CONTROLLER;
//              url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGOUT_CONTROLLER);
//            }
//            else if (button.equals("Register")) {
////                url = REGISTER_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.REGISTER_CONTROLLER);
//            }
//            else if (button.equals("Checkout Selected Items")) {
////                url = CONFIRM_CHECKOUT_CONTROLLER;
//               url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.CONFIRM_CHECKOUT_CONTROLLER);
//            }
//            else if (button.equals("Account Administration")) {
////                url = ACCOUNT_ADMINISTRATION_CONTROLLER;
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.ACCOUNT_ADMINISTRATION_CONTROLLER);
//            }
//        }
//        finally {
//            System.out.println("BtAction = " + button);
//            System.out.println("Direct to : " + url);
//            System.out.println("------------------------");
////            End steps's describing
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
