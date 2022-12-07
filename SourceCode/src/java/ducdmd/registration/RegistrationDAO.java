/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.registration;

import ducdmd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class RegistrationDAO implements Serializable {

   //    public boolean checkLogin(String username, String password)
    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, NamingException {

        Connection con = null; /// luon khoi tao bang null
        PreparedStatement stm = null; // khai bao theo chieu thuan dong theo chieu nguoc, mo sau dong truoc
        ResultSet rs = null;
        RegistrationDTO result = null;

        try {
            // 1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "SELECT username, lastname, isAdmin "
                        + "FROM Registration "
                        + "WHERE username = ? AND password = ?";
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                // 2.3 Execute Query --> ResultSet
                rs = stm.executeQuery();
                // 2.4 Process result Set
                // neu tra mot dong dung lenh if, nhieu dong dung lenh while
                if (rs.next()) {
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    result = new RegistrationDTO(username, password, fullName, role);
                }
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
    
    private List<RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }

    public void searchLastName(String searchValue)
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
                String sql = "SELECT username, password, lastname, isAdmin "
                        + "FROM Registration "
                        + "WHERE lastname LIKE ?";
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                // 2.3 Execute Query --> ResultSet
                rs = stm.executeQuery();
                // 2.4 Process result Set
                // neu tra mot dong dung lenh if, nhieu dong dung lenh while
                while (rs.next()) {
                    /// lay theo vi tri hoac lay theo ten cot --> lay theo ten cot
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");

                    RegistrationDTO dto = new RegistrationDTO(username, password, fullName, role);
                    
                    /// kiem tra accountList ton tai hay ko
                    if(this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    } // end account List has not bean exist
                    
                    this.accountList.add(dto);
                    
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
    
    public boolean deleteAccount(String username)
            throws NamingException, SQLException {
        boolean result = false;
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        
        try {
            // 1. Connect DB
            /// 1.1 GetConnection by calling DBHelper
            con = DBHelper.makeConnection();
            /// 1.2 Check available
            if(con!=null) {
                // 2. CRUD
                /// 2.1 Create SQL String
                String SQL = "DELETE FROM Registration "
                        + "WHERE username = ?";
                /// 2.2 Create Stmt Oj
                stmt = con.prepareStatement(SQL);
                stmt.setString(1, username);
                /// 2.3 ExcuteQuery/Update --> Result
                int effectedRow = stmt.executeUpdate();
                /// 2.4 Process Result
                if(effectedRow>0) {
                    result = true;
                }
            }
            
        }
        finally {
            if(stmt!=null) {
                stmt.close();
            }
            if(con!=null) {
                con.close();
            }
        }
        
        return result;
    }
    
    public boolean updateAccountPassword(String username, String password)
            throws NamingException, SQLException {
        boolean result = false;
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        
        try {           
            // 1. Connect DB
            /// 1.1 GetConnection by calling DBHelper
            con = DBHelper.makeConnection();
            /// 1.2 Check available
            if(con!=null) {
                // 2. CRUD
                /// 2.1 Create SQL String
                String SQL = "UPDATE Registration "
                        + "SET password = ? "
                        + "WHERE username = ?";
                /// 2.2 Create Stmt Oj
                stmt = con.prepareStatement(SQL);
                stmt.setString(1, password);
                stmt.setString(2, username);
                /// 2.3 ExcuteQuery/Update --> Result
                int affectedRow = stmt.executeUpdate();
                /// 2.4 Process Result
                if(affectedRow>0) {
                    result = true;
                }
            }
            
        }
        finally {
            if(stmt!=null) {
                stmt.close();
            }
            if(con!=null) {
                con.close();
            }
        }
        
        return result;
    }
    
    public boolean updateAccountRole(String username, boolean role)
            throws NamingException, SQLException {
        boolean result = false;
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        
        try {           
            // 1. Connect DB
            /// 1.1 GetConnection by calling DBHelper
            con = DBHelper.makeConnection();
            /// 1.2 Check available
            if(con!=null) {
                // 2. CRUD
                /// 2.1 Create SQL String
                String SQL = "UPDATE Registration "
                        + "SET isAdmin = ? "
                        + "WHERE username = ?";
                /// 2.2 Create Stmt Oj
                stmt = con.prepareStatement(SQL);
                stmt.setBoolean(1, role);
                stmt.setString(2, username);
                /// 2.3 ExcuteQuery/Update --> Result
                int affectedRow = stmt.executeUpdate();
                /// 2.4 Process Result
                if(affectedRow>0) {
                    result = true;
                }
            }
            
        }
        finally {
            if(stmt!=null) {
                stmt.close();
            }
            if(con!=null) {
                con.close();
            }
        }
        
        return result;
    }

    public boolean createAccount(RegistrationDTO dto) /// ko nen truyen qua nhieu tham, nhung tham so lien quan den nhau --> object for parameter
        throws NamingException, SQLException {
        boolean result = false;
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        
        try {
            // 1. Connect DB
            /// 1.1 GetConnection by calling DBHelper
            con = DBHelper.makeConnection();
            /// 1.2 Check available
            if(con!=null) {
                // 2. CRUD
                /// 2.1 Create SQL String
                String SQL = "INSERT INTO Registration("
                        + "username, password, lastname, isAdmin"
                        + ") "
                        + "VALUES ("
                        + "?, ?, ?, ?"
                        + ")";
                /// 2.2 Create Stmt Oj
                stmt = con.prepareStatement(SQL);
                stmt.setString(1, dto.getUsername());
                stmt.setString(2, dto.getPassword());
                stmt.setString(3, dto.getFullName());
                stmt.setBoolean(4, dto.isRole());
                
                /// 2.3 ExcuteQuery/Update --> Result
                int effectedRow = stmt.executeUpdate();
                /// 2.4 Process Result
                if(effectedRow>0) {
                    result = true;
                }
            }
            
        }
        finally {
            if(stmt!=null) {
                stmt.close();
            }
            if(con!=null) {
                con.close();
            }
        }
        
        return result;
    }

    private RegistrationDTO getAccount(String username) 
            throws NamingException, SQLException {
        RegistrationDTO result = null;
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            // 1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                // 2. CRUD
                // 2.1 Create SQL string
                String sql = "SELECT username, password, lastname, isAdmin "
                        + "FROM Registration "
                        + "WHERE username = ?";
                // 2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                // 2.3 Execute Query --> ResultSet
                rs = stm.executeQuery();
                // 2.4 Process result Set
                // neu tra mot dong dung lenh if, nhieu dong dung lenh while
                if (rs.next()) {
                    /// lay theo vi tri hoac lay theo ten cot --> lay theo ten cot
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");

                    RegistrationDTO dto = new RegistrationDTO(username, password, fullName, role);
                    
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
}
