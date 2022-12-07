/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.controller;

import ducdmd.cart.CartObject;
import ducdmd.product.ProductObject;
import ducdmd.orders.OrdersDAO;
import ducdmd.product.ProductDAO;
import ducdmd.product.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ducdmd.orderDetails.OrderDetailsDAO;
import ducdmd.orderDetails.OrderDetailsDTO;
import ducdmd.preparedOrder.PreparedOrderList;
import ducdmd.preparedOrder.PreparedOrder;
import ducdmd.orders.OrdersDTO;
import ducdmd.preparedOrder.PreparedOrderCheckoutError;
import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 *
 * @author MSI
 */
public class CheckOutServlet extends HttpServlet {

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

        String url = siteMaps.getProperty(MyApplicationConstants.CheckOutFeature.CHECKOUT_FAIL_PAGE);

        int preparedOrderId = Integer.parseInt(request.getParameter("preparedOrderID"));
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhoneNumber");
        String address = request.getParameter("txtAddress");
        boolean checkOutSuccess = false;
        PreparedOrderCheckoutError preparedOrderCheckoutError = new PreparedOrderCheckoutError();

        try {
            // Cases:
            // - Nguoi dung giu thong tin cua trang Confirm Checkout co chua san pham da remove khoi cart
            // ---> neu cua hang still enough san pham cung cap cho nguoi dung thi VAN ORDER theo data cua trang Confirm Checkout
            // (vi nguoi dung da confirm va neu minh co the dap ung --> dap ung)
            // - Trong truong hop, khong enough san pham dap ung cho cust --> thong bao cho user de move to cart & re-confirm checkout
            // - Tac vu bat buoc: Sau khi checkout
            // ---- update DB
            // ---- neu san pham co trong gio hang --> remove

            //0. Check user's errors           
            boolean userErrorFound = false;
            name = name.trim();
            phone = phone.trim();
            address = address.trim();

            if (name.length() < 4 || name.length() > 30) {
                userErrorFound = true;
                preparedOrderCheckoutError.setNameLengthError("Name requires from 4 to 30 chars");
            }
            String phoneNumberRegex = siteMaps.getProperty(MyApplicationConstants.CheckOutFeature.PHONE_NUMBER_REGEX);
            if (phone.matches(phoneNumberRegex) == false) {
                userErrorFound = true;
                preparedOrderCheckoutError.setPhoneNumberIsNotValid("Phone number requires from 10 to 12 digits");
            }

            if (address.isEmpty()) {
                userErrorFound = true;
                preparedOrderCheckoutError.setAddressLengthError("Address cannot be empty.");
            }

            if (userErrorFound) {
                request.setAttribute("CHECKOUT_ERROR", preparedOrderCheckoutError);
                request.setAttribute("PREPARED_ORDER_ID", preparedOrderId);
                // --> re-confirm checkout
                url = siteMaps.getProperty(MyApplicationConstants.CheckOutFeature.RE_CONFIRM_CHECKOUT_ACTION);
            } // end or user's error exist
            else {
                //1. system go to order queue's placement
                HttpSession session = request.getSession(false);
                if (session != null) {
                    //2. system take order queue
                    PreparedOrderList preparedOrderList = (PreparedOrderList) session.getAttribute("PREPARED_ORDER_LIST");

                    if (preparedOrderList != null) {
                        //3. system determine a prepared order of the cust
                        PreparedOrder preparedOrder = preparedOrderList.gerOrderQueueItem(preparedOrderId);

                        if (preparedOrder != null) {

                            //4. system check enough products for the order or not
                            List<ProductObject> products = preparedOrder.getProductsInOrder();
                            if (products != null) {
                                ProductDAO productDAO = new ProductDAO();
                                OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                                boolean enoughQuantity = true;

                                for (ProductObject product : products) {
                                    String sku = product.getSku();
                                    int requestQuantiy = product.getQuantity();

                                    ProductDTO dto = productDAO.getProduct(sku);
                                    int orderedQuantity = orderDetailsDAO.getOrderedQuantityOf(sku);
                                    int alreadyQuantity = dto.getQuantity() - orderedQuantity;

                                    if (requestQuantiy > alreadyQuantity) {
                                        enoughQuantity = false;
                                        break;
                                    }
                                }
//                        System.out.println("Enough quantity : " + enoughQuantity);
                                if (enoughQuantity == true) { // everything is already to record a order                                    
                                    // if already enough product --> process for cust's request
                                    // 5. record a order
                                    /// 5.1 create a new order
                                    OrdersDAO orderDAO = new OrdersDAO();
                                    float totalOfOrder = preparedOrder.getTotal();

                                    OrdersDTO orderDTO = new OrdersDTO();
                                    orderDTO.setDateBuy(new Timestamp(System.currentTimeMillis()));
                                    orderDTO.setTotal(totalOfOrder);
                                    orderDTO.setName(name);
                                    orderDTO.setAddress(address);
                                    orderDTO.setPhone(phone);

                                    // get username of current cust to record into DB
                                    String username = null;
                                    RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
                                    if (user != null) {
                                        username = user.getUsername();
                                    }
                                    orderDTO.setUsername(username); /// record username buy this order

                                    int orderId = orderDAO.createNewOrder(orderDTO); // create a new order in Orders table in DB
                                    orderDTO.setOrderID(orderId); // update the orderID for orderDTO

                                    /// 5.2 record order: item by item
                                    CartObject cart = (CartObject) session.getAttribute("CART"); // get CART for remove item after each OrderDetails

                                    for (ProductObject product : products) {
                                        String sku = product.getSku();
                                        /// 5.2.1 create an order detail
                                        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
                                        orderDetailsDTO.setOrderId(orderId);
                                        orderDetailsDTO.setSku(sku);
                                        orderDetailsDTO.setPrice(product.getPrice());
                                        orderDetailsDTO.setQuantiy(product.getQuantity());
                                        orderDetailsDTO.setTotal(product.getTotal());

                                        orderDetailsDAO.createOrderDetail(orderDetailsDTO);

                                        /// 5.2.2 remove product out CART if any
                                        if (cart != null) {
                                            cart.removeItemFromCart(sku);
                                        }
                                    }

                                    // 6. System inform checkout successfully
                                    checkOutSuccess = true;
                                    request.setAttribute("PREPARED_ORDER", preparedOrder);
                                    request.setAttribute("ORDER_DTO", orderDTO);
//                                    url = "checkOutSuccess.jsp";
                                    url = siteMaps.getProperty(
                                            MyApplicationConstants.CheckOutFeature.CHECKOUT_SUCCESS_PAGE
                                    );                                    
                                } // end of enough product for order --> CHECKOUT SUCCESS
                                else {
                                    preparedOrderCheckoutError.setNotEnoughProductForOrder("There is not enough quantity for some product.");
                                } // end of fail to order because not enough quantity for order 
                            } // end of productList does exist
                            else {
                                preparedOrderCheckoutError.setProductListIsNotExisted("Product List is is not exist.");
                            }
                        } // end of preparedOrder is found
                        else {
                            preparedOrderCheckoutError.setPrepareOrderIsNotFound("Not found the prepared order in list.");
                        }
                    } else {
                        preparedOrderCheckoutError.setPrepareOrderIsNotFound("Not found prepared order list.");
                    }

                } // end of preparedOrderList is exist
                else {
                    preparedOrderCheckoutError.setPrepareOrderIsNotFound("Session is not exist");
                }

                if (checkOutSuccess == false) {
                    // url = checkOutFail.jsp --> inform to user for re-checkout                            
                    request.setAttribute("CHECKOUT_ERROR", preparedOrderCheckoutError);
                } // end of checkout fail
            }
        } catch (SQLException ex) {
            log("CheckOutServlet _SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("CheckOutServlet _Naming: " + ex.getMessage());
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
