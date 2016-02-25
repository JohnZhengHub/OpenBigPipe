package org.opensjp.openbigpipe.core.concurrent;

import java.util.Map;
import java.util.concurrent.Callable;

import org.opensjp.openbigpipe.annotation.ExecuteType;
import org.opensjp.openbigpipe.annotation.Priority;
import org.opensjp.openbigpipe.beans.PageletBean;
import org.opensjp.openbigpipe.extention.PageletCache;
import org.opensjp.openbigpipe.extention.PageletDowngrade;
import org.opensjp.openbigpipe.extention.PageletExtendtionHolder;
import org.opensjp.openbigpipe.factory.ViewResourceFactory;
import org.opensjp.openbigpipe.utils.InjectUtils;
import org.opensjp.openbigpipe.view.ViewRendererContext;
import org.opensjp.openbigpipe.view.ViewResource;
/**
 * 执行每个Pagelet的任务，并将pagelet对应的页面进行渲染，返回渲染的页面结果．
 * @author John Zheng 
 *　
 */
public class PageletWorker implements Callable<String>{
	//要执行的pagelet 
    private PageletBean pageletBean;
    //页面渲染器上下文
    private ViewRendererContext rendererContext;
    //BigPipe 的执行方式
    private ExecuteType executeType;
    //抛出异常的异常　应急处理
    private PageletDowngrade downgrade;
    //缓存
	private PageletCache cache;
	private Priority priority;
    public PageletWorker(PageletBean pageletBean,ExecuteType executeType) {
    	priority = pageletBean.getPriority();
    	this.pageletBean = pageletBean;
    	this.executeType = executeType;
    	PageletExtendtionHolder extendHolder = PageletExtendtionHolder.singletonInstance();
    	this.downgrade = extendHolder.getDowngrade();
    	this.cache = extendHolder.getCache();
    }
    @Override
	public String call() throws Exception {
    	String renderResult = "";
    	try {
    		if(cache != null){
    			//从缓冲中获取
    			renderResult = cache.getCachedPageletResult(pageletBean); 	
    			if(renderResult != null)
    				return renderResult;
        	}
    		
    		String view = pageletBean.execute();
    		if(view == null)
    			throw new NullPointerException();
    		
    		ViewResource viewResource = ViewResourceFactory.newInstance(view);
    		rendererContext = new ViewRendererContext(viewResource.getViewType());
    		Map<String,Object> modelForView = InjectUtils.getFieldValueWithAnnoParamFromObject(pageletBean.getPargletObject());
            renderResult = rendererContext.render(viewResource, modelForView);
            if(executeType == ExecuteType.BIGPIPE)//如果是bigPipe则转化为json形式
            	renderResult = buildJsonResult(renderResult);
            
            if(cache != null)//备份
            	cache.backup(pageletBean, renderResult);
        	
            return renderResult;
        }catch (Throwable e) {
        	if(downgrade != null ){//如果有异常处理方案，则查找对应解决方案
        		renderResult = downgrade.downgrade(pageletBean);
        		if(renderResult != null){
        			return renderResult;
        		}
        	}
        	//如果发生异常　则输出异常.
        	renderResult = generateExceptionToPrintStack(e);
		}
		return renderResult;
	}
    /**
     * 将结果转化为json形式　
     * @param result
     * @return
     */
    private String buildJsonResult(String result) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"application/javascript\">")
                .append("\nreplace(\"")
                .append(pageletBean.getDivId())
                .append("\",\'")
                .append(result.replaceAll("\n","")).append("\');\n</script>");
        return (String) sb.toString();
    }
    /**
     * 输出异常
     * @param e
     * @return
     */
    private String generateExceptionToPrintStack(Throwable e) {
    	StringBuilder result = new StringBuilder();
        result.append("<div style=\"background-color: #eee;font-size:9px;font-family: " +
                "Consolas,Menlo,Monaco;height:250px;overflow:scroll\">");
        result.append("<font style=\"color:red\">")
                .append(e.toString())
                .append("</font></br>");
        for (StackTraceElement element : e.getStackTrace()) {
            result.append(element.toString() + "</br>");
        }
        result.append("</div>");
        return buildJsonResult(result.toString());
    }
    
	public Priority getPriority() {
		return priority;
	}
    
}
