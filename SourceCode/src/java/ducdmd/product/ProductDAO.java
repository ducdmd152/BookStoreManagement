package ducdmd.product;

import ducdmd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MSI
 */
public class ProductDAO implements Serializable {
    private List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void loadAvailableProducts()
            throws SQLException, NamingException {

        Connection con = null; /// luon khoi tao bang null
        PreparedStatement stm = null; // khai bao theo chieu thuan dong theo chieu nguoc, mo sau dong truoc
        ResultSet rs = null;

        try {
            // 1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "SELECT sku, name, description, quantity, price, status "
                        + "FROM Product "
                        + "WHERE status = 'True' ";
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                // 2.3 Execute Query --> ResultSet
                rs = stm.executeQuery();
                // 2.4 Process result Set
                // neu tra mot dong dung lenh if, nhieu dong dung lenh while
                while (rs.next()) {
                    /// lay theo vi tri hoac lay theo ten cot --> lay theo ten cot
                    String sku = rs.getString("sku");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    boolean status = rs.getBoolean("status");

                    ProductDTO dto = new ProductDTO(sku, name, description, quantity, price, status);
                    
                    /// kiem tra accountList ton tai hay ko
                    if(this.productList == null) {
                        this.productList = new ArrayList<>();
                    } // end account List has not bean exist
                    
                    this.productList.add(dto);
                    
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
    }

    public ProductDTO getProduct(String sku)
            throws SQLException, NamingException {
        ProductDTO result = null;
        
        Connection con = null; /// luon khoi tao bang null
        PreparedStatement stm = null; // khai bao theo chieu thuan dong theo chieu nguoc, mo sau dong truoc
        ResultSet rs = null;
        

        try {
            // 1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "SELECT name, description, quantity, price "
                        + "FROM Product "
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
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    boolean status = true;

                    ProductDTO dto = new ProductDTO(sku, name, description, quantity, price, status);
                    
                    result = dto;
                    
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

//    public boolean updateQuantity(String sku, int quantity) 
//            throws SQLException, NamingException {
//        boolean result = false;
//        
//        Connection con = null;
//        PreparedStatement stmt = null;
//        
//        
//        try {
//            // 1. Connect DB
//            /// 1.1 GetConnection by calling DBHelper
//            con = DBHelper.makeConnection();
//            /// 1.2 Check available
//            if(con!=null) {
//                // 2. CRUD
//                /// 2.1 Create SQL String
//                String SQL = "UPDATE Product "
//                        + "SET quantity = ? "
//                        + "WHERE sku = ?";
//                /// 2.2 Create Stmt Oj
//                stmt = con.prepareStatement(SQL);
//                stmt.setInt(1, quantity);
//                stmt.setString(2, sku);
//                /// 2.3 ExcuteQuery/Update --> Result
//                int affectedRow = stmt.executeUpdate();
//                /// 2.4 Process Result
//                if(affectedRow>0) {
//                    result = true;
//                }
//            }
//            
//        }
//        finally {
//            if(stmt!=null) {
//                stmt.close();
//            }
//            if(con!=null) {
//                con.close();
//            }
//        }
//        
//        return result;
//    }
}
