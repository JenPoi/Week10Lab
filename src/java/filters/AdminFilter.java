/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import businesslogic.UserService;
import domainmodel.Role;
import domainmodel.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 617702
 */
public class AdminFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        boolean isAdmin = false;
        
        HttpSession session = ((HttpServletRequest)request).getSession();
        String username = (String)session.getAttribute("username");
       if (username != null) {
            
           UserService us = new UserService();
            try {
                User user = us.get(username);
                Role role = user.getRole();
                if(role.getRoleID() == 1)
                {
                    chain.doFilter(request, response);
                }
                else
                {
                    ((HttpServletResponse)response).sendRedirect("home");
                }
            } catch (Exception ex) {
                Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            // yes, go onwards to the servlet or next filter
          
        } else {
            // get out of here!
            ((HttpServletResponse)response).sendRedirect("login");
        }
        
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
   
        }
    
}
