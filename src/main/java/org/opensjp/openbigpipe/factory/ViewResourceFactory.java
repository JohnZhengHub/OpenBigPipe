package org.opensjp.openbigpipe.factory;

import org.opensjp.openbigpipe.exception.ViewResourceException;
import org.opensjp.openbigpipe.view.FreemarkerViewResource;
import org.opensjp.openbigpipe.view.ViewResource;

public class ViewResourceFactory {
	/**
	 * 根据路径类型，生成相应的视图资源
	 * @param viewPath
	 * @return
	 */
	public static ViewResource newInstance(String viewPath){
		int index = viewPath.lastIndexOf('.');
		String endStr = viewPath.substring(index+1);
		ViewResource viewResource = null;
		if("ftl".equals(endStr))
			 viewResource = new FreemarkerViewResource(viewPath);
		
		if(viewResource == null ){
			throw new ViewResourceException("openBigPipe can't support the file type of view: \""+endStr+"\". "
					+ "check the file "+viewPath+".");
		}else if(!viewResource.exist()){
			throw new ViewResourceException("ViewPath error: can't find "+viewPath);
		}
		
		return viewResource;
	}
}
