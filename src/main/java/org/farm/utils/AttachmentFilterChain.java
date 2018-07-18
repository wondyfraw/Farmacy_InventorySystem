package org.farm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AttachmentFilterChain implements FilterChain {

	private FilterChain filterChain;
	private List<Filter> filterList = new ArrayList<Filter>();
	private Iterator<Filter> iterator;
	private Log log = LogFactory.getLog(this.getClass());

	public AttachmentFilterChain(FilterChain chain) {
		this.filterChain = chain;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {

		if (iterator == null) {
			iterator = filterList.iterator();
		}
		if (iterator.hasNext()) {
			try {
				iterator.next().doFilter(request, response, this);
			} catch (IOException e) {
				log.error(e.getMessage());
			} catch (ServletException e) {
				log.error(e.getMessage());
			}
		} else {
			try {
				filterChain.doFilter(request, response);
			} catch (IOException e) {
				log.error(e.getMessage());
			} catch (ServletException e) {
				log.error(e.getMessage());
			}
		}
	}

	public void addFilter(Filter filter) {
		if (iterator != null) {
			throw new IllegalStateException();
		}
		filterList.add(filter);
	}
}
