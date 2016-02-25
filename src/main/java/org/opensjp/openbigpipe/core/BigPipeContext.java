package org.opensjp.openbigpipe.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 上下文环境
 * @author zjl
 *
 */
public class BigPipeContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String packageScan;
	private String configPath;
	public BigPipeContext(HttpServletRequest request, HttpServletResponse response, String packageScan,
			String configPath) {
		super();
		this.request = request;
		this.response = response;
		this.packageScan = packageScan;
		this.configPath = configPath;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getPackageScan() {
		return packageScan;
	}
	public void setPackageScan(String packageScan) {
		this.packageScan = packageScan;
	}
	public String getConfigPath() {
		return configPath;
	}
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	
}
