/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.orderDetails;

import ducdmd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class OrderDetailsDAO implements Serializable {   
    public boolean createOrderDetail(OrderDetailsDTO orderDetailsDTO)
            throws NamingException, SQLException {
        boolean result = false;
        Connection con = null; /// luon khoi tao bang null
        PreparedStatement stm = null; // khai bao theo chieu thuan dong theo chieu nguoc, mo sau dong truoc

        try {
            // 1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "INSERT INTO OrderDetails("
                        + "order_id, sku, price, quantity, total"
                        + ") "
                        + "VALUES("
                        + "?, ?, ?, ?, ?"
                        + ")";
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderDetailsDTO.getOrderId());
                stm.setString(2, orderDetailsDTO.getSku());
                stm.setFloat(3, orderDetailsDTO.getPrice());
                stm.setInt(4, orderDetailsDTO.getQuantiy());
                stm.setFloat(5, orderDetailsDTO.getTotal());

                // 2.3 Execute Query --> Result
                int affectedRow = stm.executeUpdate();
                // 2.4 Process result Set
                if (affectedRow > 0) {
                    result = true;
                } /// end traverse Result Set
            } // end connection is available
        } finally { /// must closed all objects (con <- stm <- rs)  that are created after process had finished

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
//        result = insertNewOrderDetail(
//                orderDetailsDTO.getOrderId(),
//                orderDetailsDTO.getSku(),
//                orderDetailsDTO.getQuantiy(),
//                orderDetailsDTO.getPrice(),
//                orderDetailsDTO.getTotal());
        return result;
    }
    
    public int getOrderedQuantityOf(String sku) 
            throws SQLException, NamingException {
        int result = 0;
        
        Connection con = null; /// luon khoi tao bang null
        PreparedStatement stm = null; // khai bao theo chieu thuan dong theo chieu nguoc, mo sau dong truoc
        ResultSet rs = null;
        

        try {
            // 1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "SELECT SUM(quantity) AS orderedQuantity "
                        + "FROM OrderDetails "
                        + "WHERE sku = ?";
                
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, sku);
                // 2.3 Execute Query --> ResultSet
                rs = stm.executeQuery();
                // 2.4 Process result Set
                // neu tra mot dong dung lenh if, nhieu dong dung lenh while
                if (rs.next()) {
                    /// lay theo vi tri hoac lay theo ten cot --> lay theo ten cot
                    int orderedQuantity = rs.getInt("orderedQuantity");
                    
                    result = orderedQuantity;
                    
                } /// end traverse Result Set
            } // end connection is available
        }
        finally { /// must closed all objects (con <- stm <- rs)  that are created after process had finished
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        
        return result;
    }
//    public boolean insertNewOrderDetail(int order_id, String sku, int quantity, float price, float total)
//            throws NamingException, SQLException {
//        boolean result = false;
//        Connection con = null; /// luon khoi tao bang null
//        PreparedStatement stm = null; // khai bao theo chieu thuan dong theo chieu nguoc, mo sau dong truoc
//
//        try {
//            // 1. connect DB
//            con = DBHelper.makeConnection();
//            if (con != null) {
//                // 2. CRUD
//                // 2.1 Create SQL string
//                String sql = "INSERT INTO OrderDetails(order_id, sku, quantity, price, total) "
//                        + "VALUES  (?, ?, ?, ?, ?)";
//                // 2.2 Create Statement Object
//                stm = con.prepareStatement(sql);
//                stm.setInt(1, order_id);
//                stm.setString(2, sku);
//                stm.setInt(3, quantity);
//                stm.setFloat(4, price);
//                stm.setFloat(5, total);
//
//                // 2.3 Execute Query --> Result
//                int affectedRow = stm.executeUpdate();
//                // 2.4 Process result Set
//                if (affectedRow > 0) {
//                    result = true;
//                } /// end traverse Result Set
//            } // end connection is available
//        } finally { /// must closed all objects (con <- stm <- rs)  that are created after process had finished
//
//            if (stm != null) {
//                stm.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//
//        return result;
//    }

}
