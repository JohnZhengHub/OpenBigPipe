package org.opensjp.openbigpipe.core.concurrent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.beans.PageletBean;
import org.opensjp.openbigpipe.factory.ThreadPoolExecutorFactoryImpl;
import org.opensjp.openbigpipe.view.ViewRendererContext;
import org.opensjp.openbigpipe.view.ViewResource;
/**
 * 后台采用并发方式执行，最后将整个页面渲染完成一起返回给客户端
 * @author John Zheng
 *
 */
public class ConcurrentPipeExecutor implements Executor {
    private ThreadPoolExecutor executor = ThreadPoolExecutorFactoryImpl.
            newInstance().instanceOfDefaultConfig();
    private ViewRendererContext rendererContext;
   
	@Override
	public void execute(BigPipeBean bigPipeBean, PageletBean pageletBean) {
		
	}

	@Override
	public void execute(BigPipeBean bigPipeBean, List<PageletBean> pageletBeans) throws Exception {
		//暂时没有加入
		Map<Object,String> pageletResults = new HashMap<Object,String>();
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
		for(PageletBean pb : pageletBeans){
			Future<String> future = completionService.submit(new PageletWorker(pb,bigPipeBean.getType()));
			pageletResults.put(future, pb.getKey());
		}
		Map<String,Object> context = new HashMap<String,Object>();
		//获得结果直接显示. 
		for(int i = 0; i < pageletBeans.size(); i++){
			Future<String> future = completionService.take();
			String result = future.get();
			context.put(pageletResults.get(future), result);
		}
		context.putAll(bigPipeBean.getFieldParams());
		doResponse(bigPipeBean,context);
	}
	/**
	 * 返回渲染结果　
	 * @param bigPipeBean
	 * @param executeResults
	 * @throws IOException
	 */
	private void doResponse(BigPipeBean bigPipeBean, Map<String, Object> executeResults) throws IOException {
		ViewResource viewResource = bigPipeBean.getViewResource();
		rendererContext = new ViewRendererContext(viewResource.getViewType());
        String result = rendererContext.render(viewResource, executeResults).toString();
        PrintWriter writer =  bigPipeBean.getResponse().getWriter();
        writer.println(result);
        writer.flush();
        writer.close();
    }
}
