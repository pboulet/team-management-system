/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tms.models.User;

/**
 * 
 * All requests are filtered through this Servlet Filter before being passed
 * to a Servlet.
 * We use it to redirect requests to protected resources to the login page.
 * 
 * Look in web.xml for the filter mapping.
 */
public class AuthenticationFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthenticationFilter() {
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
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession ses = req.getSession(false);  
        String reqURI = req.getRequestURI();
        // redirects to login page if an attempt to access a page in /protected folder  
        // is made by an anonymous (not logged) user
        
        //TODO: replace securePath by "protected" when ready -P
        if (reqURI.contains("/protected/")){
            if(ses == null || (ses != null && ses.getAttribute("User") == null)){
                String loginURL = req.getContextPath() + "/faces/login.xhtml";
                res.sendRedirect(loginURL);
            }
            else {
                if(reqURI.contains("/student/")){
                    User u = (User)ses.getAttribute("User");
                    if(!u.isStudent()){
                        String homeURL = req.getContextPath() + "/faces/protected/home.xhtml";
                        res.sendRedirect(homeURL);
                    }
                    else{
                        chain.doFilter(request, response);
                    }
                }
                else if(reqURI.contains("/instructor/")){
                    User u = (User)ses.getAttribute("User");
                    if(!u.isInstructor()){
                        String homeURL = req.getContextPath() + "/faces/protected/home.xhtml";
                        res.sendRedirect(homeURL);
                    }
                    else{
                        chain.doFilter(request, response);
                    }
                }
                else{
                    chain.doFilter(request, response);
                }
            }
        }
        else {
            chain.doFilter(request, response);
        }  
            
    }

    /**
     * Return the filter configuration object for this filter.
     * @return 
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
    @Override
    public void destroy() {        
    }

    /**
     * Init method for this filter
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuilder sb = new StringBuilder("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
}
