package org.farm.fms.entity.ejb;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.entity.beans.LoginAuthenticationBean;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set then request is being redirected to the
 * login.xhml page.
 * 
 * @author Wonde
 *
 */

public class LoginFilter implements Filter {
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 * 
	 */

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		// Principal princ = request.getUserPrincipal();
		// String username = princ.getName();
		InitialContext ic;
		boolean isUserLoggedIn = false;
		try {
			ic = new InitialContext();
			if (session != null) {
				LoginAuthenticationBean loginAuthenticationBean = null;
				// if (((HttpServletRequest) req).getSession().getAttribute("loginAuthenticationBean") != null) {
				if (session.getAttribute("loginAuthenticationBean") != null) {
					loginAuthenticationBean = (LoginAuthenticationBean) session.getAttribute("loginAuthenticationBean");
					isUserLoggedIn = loginAuthenticationBean.isLoggedIn();
				}
				/*
				 * else { loginAuthenticationBean = new LoginAuthenticationBean(username);
				 * session.setAttribute("loginAuthenticationBean", loginAuthenticationBean); isUserLoggedIn =
				 * loginAuthenticationBean.isLoggedIn();
				 * 
				 * }
				 */

				if (loginAuthenticationBean == null || !isUserLoggedIn) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/public/login1.xhtml");
				}
				chain.doFilter(request, response);
			} else {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/public/login1.xhtml");
			}
		} catch (NamingException e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
