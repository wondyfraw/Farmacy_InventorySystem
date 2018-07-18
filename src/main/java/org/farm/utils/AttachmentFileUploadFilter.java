package org.farm.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.webapp.filter.FileUploadFilter;

public class AttachmentFileUploadFilter implements Filter {

	private Log log = LogFactory.getLog(AttachmentFileUploadFilter.class);
	private FileUploadFilter primefacesFileUploadFilter;
	private String primefaces;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		boolean isMultiPart = ServletFileUpload.isMultipartContent(httpServletRequest);
		if (isMultiPart) {

			AttachmentFilterChain chain = new AttachmentFilterChain(filterChain);
			chain.addFilter(primefacesFileUploadFilter);

			chain.doFilter(request, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		primefacesFileUploadFilter = new FileUploadFilter();
		primefacesFileUploadFilter.init(filterConfig);
		primefaces = filterConfig.getInitParameter("id");

	}

}
