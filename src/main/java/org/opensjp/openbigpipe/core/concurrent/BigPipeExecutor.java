package org.opensjp.openbigpipe.core.concurrent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.annotation.Priority;
import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.beans.PageletBean;
import org.opensjp.openbigpipe.core.BigpipeViewFrameStrategy;
import org.opensjp.openbigpipe.factory.BigpipesupportStrategyFactory;
import org.opensjp.openbigpipe.factory.ThreadPoolExecutorFactoryImpl;
import org.opensjp.openbigpipe.view.ViewRendererContext;
import org.opensjp.openbigpipe.view.ViewResource;

/**
 * 按BigPipe算法执行的　执行器
 * @author John Zheng
 *
 */
public class BigPipeExecutor implements Executor{
	private static Logger logger = Logger.getLogger(BigPipeExecutor.class);  
	private ThreadPoolExecutor executor;
	private static BigpipeViewFrameStrategy bigpipeSupportStrategy;
	private ViewRendererContext rendererContext;
	public BigPipeExecutor(){
		executor = ThreadPoolExecutorFactoryImpl.newInstance().instanceOfDefaultConfig();
		bigpipeSupportStrategy = BigpipesupportStrategyFactory.newInstance();
	}
	
	@Override
	public void execute(BigPipeBean bigPipeBean, PageletBean pageletBean)  throws Exception{
		
	}
	@Override
	public void execute(BigPipeBean bigPipeBean, List<PageletBean> pageletBeans) throws Exception {
		
 		CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
		//渲染页面框架及返回给客户端
		PrintWriter writer = getWrite(bigPipeBean);
		renderPageFrameworkAndFlush(bigPipeBean,writer,bigPipeBean.getFieldParams());
		logger.info("Finish to flush view frame.");
		
		int hSzie = 0, nSzie = 0;
		Map<Object,PageletBean> pageletResults = new HashMap<Object,PageletBean>();
		//多线程并行执行　各个pagelet
		hSzie = submitTaskForDifferentPriority(Priority.HEIGHT,bigPipeBean, pageletBeans, 
				completionService,pageletResults);
		nSzie = submitTaskForDifferentPriority(Priority.NORMALL,bigPipeBean, pageletBeans,
				completionService,pageletResults);
		submitTaskForDifferentPriority(Priority.LOW,bigPipeBean, pageletBeans,
				completionService,pageletResults);
		
		List<String> normallResult = new ArrayList<String>();
		List<String> lowResult = new ArrayList<String>();
		
		//将结果按优先级顺序返回给前台
		for(int i = 0; i < pageletBeans.size(); i++){
			Future<String> future = completionService.take();
			String result = future.get();
			PageletBean pb = pageletResults.get(future);
			switch(pb.getPriority()){
				case HEIGHT: {
					flush(writer,result);
					hSzie--;
					break;
				}
				case NORMALL:{
					//处理优先级为NORMALL的结果
					nSzie = dealResult(writer, hSzie, nSzie, normallResult, result);	
					break;
				}
				case LOW:{
					dealResult(writer, nSzie, nSzie, lowResult, result);
					break;
				}
			}
		}
		
		for(String result : normallResult){
			flush(writer,result);
		}
		for(String result : lowResult){
			flush(writer,result);
		}
		
		closeHtml(writer);
		logger.info("Finished all work of view flush.");
	}

	private int dealResult(PrintWriter writer, int hSzie, int nSzie, List<String> priorityResult, String result) {
		if(hSzie == 0){
			priorityResult.add(result);
			for(String str : priorityResult){
				flush(writer,str);
				nSzie--;
			}
			priorityResult.clear();			
		}else{
			priorityResult.add(result);
		}
		return nSzie;
	}

	private int submitTaskForDifferentPriority(Priority priority,BigPipeBean bigPipeBean, List<PageletBean> pageletBeans,
			CompletionService<String> completionService, Map<Object, PageletBean> pageletResults) {
		int size = 0;
		for(PageletBean pb : pageletBeans){
			if(pb.getPriority() == priority){
				Future<String> future = completionService.submit(new PageletWorker(pb,bigPipeBean.getType()));
				pageletResults.put(future, pb);
				size++;
			}
		}
		return size;
	}
	
	/**
	 * 获取　PrintWriter
	 * @param bigPipeBean　BigPipeBeanDefinition
	 * @return
	 * @throws IOException
	 */
	private PrintWriter getWrite(BigPipeBean bigPipeBean) throws IOException {
        PrintWriter writer = bigPipeBean.getResponse().getWriter();
        return writer;
    }
	
	/**
	 * 渲染框架
	 * @param writer
	 * @param executeResults
	 */
	private void renderPageFrameworkAndFlush(BigPipeBean bigPipeBean,PrintWriter writer, Map<String, Object> modelForView) {
		ViewResource ViewResource = bigPipeBean.getViewResource();
		rendererContext = new ViewRendererContext(ViewResource.getViewType());
        String framework =rendererContext.render(ViewResource, modelForView);
        framework = bigpipeSupportStrategy.execute(framework);
        flush(writer,framework);
    }
    private void flush(PrintWriter writer, Object content) {
        writer.println(content);
        writer.flush();
    }
    /**
     * 封闭
     * @param writer
     */
    private void closeHtml(PrintWriter writer) {
        writer.println("</body>");
        writer.println("</html>");
        writer.flush();
        writer.close();
    }
}
