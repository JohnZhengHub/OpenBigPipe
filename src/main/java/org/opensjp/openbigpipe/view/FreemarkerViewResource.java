package org.opensjp.openbigpipe.view;

import org.opensjp.openbigpipe.exception.ViewResourceException;
/**
 * freeMarker 视图资源
 * @author John Zheng
 *
 */
public class FreemarkerViewResource extends ViewResource{
	private String viewType = FREEMARKER_TYPE;
	
	public FreemarkerViewResource(String viewPath){
		super(viewPath);
		if(!viewPath.endsWith(".ftl")){
			throw new ViewResourceException("FreemarkerViewRecource File name must end with \".ftl\".");
		}
	}

	@Override
	public String getViewType() {
		return viewType;
	}
}
