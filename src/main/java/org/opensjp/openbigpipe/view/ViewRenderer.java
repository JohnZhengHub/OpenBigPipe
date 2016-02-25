package org.opensjp.openbigpipe.view;

import java.util.Map;

/**
 * 视图渲染器
 * @author John Zheng
 *
 */
public interface ViewRenderer {
	
	public String render(ViewResource viewResource, Map<String, Object> modelForView);

}
