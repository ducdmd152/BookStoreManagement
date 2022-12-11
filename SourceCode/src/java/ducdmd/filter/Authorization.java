/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.filter;

import ducdmd.registration.RegistrationDAO;
import ducdmd.registration.RegistrationDTO;
import ducdmd.utils.MyApplicationConstants;
import ducdmd.utils.SHA256;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javafx.util.Pair;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MSI
 */
public class Authorization implements Filter {
    
    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public Authorization() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("Authorization:DoBeforeProcessing");
        }
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            // Check logined-user exist in session ~ for case user in a session + url without any parameter + cookie timeout
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("USER") == null) { // ~ if NOT: session already has user => try to AUTO-LOGIN with USER
                // 1. get candidate username:password pairs            
                /// by cookies
                Cookie[] cookies = req.getCookies();
                String cookieUsername = null;
                String cookiePassword = null;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) {
                            cookieUsername = cookie.getValue();
                        }

                        if (cookie.getName().equals("password")) {
                            cookiePassword = cookie.getValue();
                        }
                    }
                } // end cookies has existed

                // 2. Check valid username:password
                RegistrationDAO dao = new RegistrationDAO();

                RegistrationDTO result = null;
                String username = null;
                String password = null;

                /// Check cookie's pair  
                if (cookieUsername != null && cookiePassword != null) {                    
                    result = dao.checkLogin(cookieUsername, SHA256.getHash(cookiePassword));
                    if (result != null) {
                        username = cookieUsername;
                        password = cookiePassword;
                    }
                }

                /// 2.2 Process result
                Boolean role = null;
                if (result != null) {

                    /// 2.2.1 Saving username:password for session tracking
                    session = req.getSession(true); /// need be existed
                    session.setAttribute("USER", result);
                } // end of auto login successfully
                else {

                } // end of auto login fail
            }

            Boolean role = null; // un-logined

            session = req.getSession(); // session is existed surely
            RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
            
            if (user != null) { // logined
                role = user.isRole();            
            }

            /// 2.2.3 Authoriztion for user in session
            ducdmd.authorization.Authorization authorization = new ducdmd.authorization.Authorization();
            List<Pair<String, String>> featureNavs = authorization.getFeatureNavs(role); /// null for unlogined-user, true for admin, and false for cust
            session.setAttribute("FEATURE_NAVS", featureNavs);
            // end of already user acc exist in session
//            System.out.println("User's request from: " + role);
    }
    catch (NoSuchAlgorithmException ex) {
            log("AuthorizationFilter _PASSWORD_HASHING: " + ex.getMessage());
    }
    catch (SQLException ex) {
            log("AuthorizationFilter _SQL: " + ex.getMessage());
    }
    catch (NamingException ex) {
            log("AuthorizationFilter _Naming: " + ex.getMessage());
    }
    finally {
            
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("Authorization:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("Authorization:doFilter()");
        }
        
        doBeforeProcessing(request, response);
        
        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            log(t.getMessage());
        }
        
        doAfterProcessing(request, response);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("Authorization:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("Authorization()");
        }
        StringBuffer sb = new StringBuffer("Authorization(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
