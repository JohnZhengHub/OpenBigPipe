package org.opensjp.openbigpipe.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * BiPipeFilter 主要是获取和设置 respone 和 request
 * @author john Zheng
 *
 */
public class BigPipeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		//ServletContext context = filterConfig.getServletContext();
		//context.setAttribute("bigPipe", object);
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		BigPipeRequestContextHolder.setRequest((HttpServletRequest)request);
		BigPipeRequestContextHolder.setResponse((HttpServletResponse)response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	

}
