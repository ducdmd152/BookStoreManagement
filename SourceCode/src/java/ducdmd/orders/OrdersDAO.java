/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.orders;

import ducdmd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class OrdersDAO implements Serializable {

    public int createNewOrder()
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement stmtToGetId = null;
        ResultSet rs = null;
        int result = 0;
        
        try {
            // 1. Connect DB
            con = DBHelper.makeConnection();
            if(con!=null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "INSERT INTO Orders(dateBuy) "
                        + "VALUES (?)";
                // 2.2 Create prestmt obj
                stmt = con.prepareStatement(sql);
                stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                //2.3 Excute() --> result
                System.out.println(stmt.executeUpdate());
                int affectedRow = 5;
                //2.4 Process Result
                if(affectedRow > 0) {
                    sql = "SELECT MAX(id) AS last_inserted_id "
                        + "FROM Orders";
                    stmtToGetId = con.prepareStatement(sql);
                    
                    rs = stmtToGetId.executeQuery();
                    
                    if(rs.next()) {
                        result = rs.getInt("last_inserted_id");
                    }
                }
            }
        }
        finally {          
            if(rs!=null) {
                rs.close();
            }
            
            if(stmtToGetId!=null) {
                stmtToGetId.close();
            }
            
            if(stmt!=null) {
                stmt.close();
            }
            
            if(con!=null) {
                con.close();
            }
        }
        
        return result;
    }

    public boolean updateOrderTotal(int orderId, float total) 
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
                String sql = "UPDATE Orders "
                        + "SET total = ? "
                        + "WHERE id = ?";
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setFloat(1, total);
                stm.setInt(2, orderId);
                
                // 2.3 Execute Query --> Result
                int affectedRow = stm.executeUpdate();
                // 2.4 Process result Set
                if (affectedRow>0) {                   
                    result = true;
                } /// end traverse Result Set
            } // end connection is available
        }
        finally { /// must closed all objects (con <- stm <- rs)  that are created after process had finished

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        
        return result;
    }    

    public int createNewOrder(OrdersDTO orderDTO) 
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement stmtToGetId = null;
        ResultSet rs = null;
        int result = 0;
        
        try {
            // 1. Connect DB
            con = DBHelper.makeConnection();
            if(con!=null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "INSERT INTO Orders("
                        + "dateBuy, total, name, phone, address, username"
                        + ") "
                        + "VALUES ("
                        + "?, ?, ?, ?, ?, ?"
                        + ")";
                // 2.2 Create prestmt obj
                stmt = con.prepareStatement(sql);
                
                stmt.setTimestamp(1, orderDTO.getDateBuy());
                stmt.setFloat(2, orderDTO.getTotal());
                stmt.setString(3, orderDTO.getName());
                stmt.setString(4, orderDTO.getPhone());
                stmt.setString(5, orderDTO.getAddress());
                stmt.setString(6, orderDTO.getUsername());
                //2.3 Excute() --> result
                int affectedRow = stmt.executeUpdate();
                //2.4 Process Result
                if(affectedRow > 0) {
                    sql = "SELECT MAX(id) AS last_inserted_id "
                        + "FROM Orders";
                    stmtToGetId = con.prepareStatement(sql);
                    
                    rs = stmtToGetId.executeQuery();
                    
                    if(rs.next()) {
                        result = rs.getInt("last_inserted_id");
                    }
                }
            }
        }
        finally {          
            if(rs!=null) {
                rs.close();
            }
            
            if(stmtToGetId!=null) {
                stmtToGetId.close();
            }
            
            if(stmt!=null) {
                stmt.close();
            }
            
            if(con!=null) {
                con.close();
            }
        }
        
        return result;
    }
}
