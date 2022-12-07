/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 *
 * @author MSI
 */
public class DBHelper {
    public static Connection makeConnection()
            throws /*ClassNotFoundException*/ NamingException, SQLException {
        //1. Find Server Context - JNDI ~ Java Naming Directory Interface
        Context serverContext = new InitialContext();
        //2. Find Container Context
        Context tomcatContext = (Context)serverContext.lookup("java:comp/env");
        //3. Get DS
        DataSource ds = (DataSource) tomcatContext.lookup("MrBean");
        //4. Open connection
        Connection con = ds.getConnection();
        
        return con;
        //5. 
        
//        // 1. Load Driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        // 2. Make Connection URL String
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=JDBC;instanceName=MSSQLExpress2019";
//        // 3. Open Connection
//        Connection con = DriverManager.getConnection(url, "sa", ""); /// sa, pass = ""
//        
//        return con;
    }
    
    public static Properties getSiteMaps(String siteMapFile, ServletContext context)
            throws IOException {
        if (siteMapFile == null) {
            return null;
        }
        
        if (siteMapFile.trim().isEmpty()) {
            return null;
        }
        
        Properties result = new Properties();
        
        InputStream is = null;
        
        try {
            is = context.getResourceAsStream(siteMapFile);
            result.load(is);
             return result;
        }
        finally {
            if(is!=null) {
                is.close();
            }
        }                      
    }
}
