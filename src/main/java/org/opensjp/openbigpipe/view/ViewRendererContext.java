package org.opensjp.openbigpipe.view;import java.util.Map;

/**
 * 为{@code ViewRenderer}提供一个上下文，根据视图类型指定一个具体的{@code ViewRenderer}实现类.
 * 该部分采用策略模式，将策略模式与简单工厂模式结合．
 * @author John Zheng
 *
 */
public class ViewRendererContext {
	private ViewRenderer renderer;
	/**
	 * 根据视图类型{@code type}来构建一个视图渲染器{@code ViewRenderer}
	 * @param type 指定的视图类型
	 */
	public ViewRendererContext(String type){
		//如果时freemarker　资源则使用　freemarker渲染器
		switch(type){
		case ViewResource.FREEMARKER_TYPE:
			renderer = new FreemarkerRenderer();
			break;
		case ViewResource.JSP_TYPE:
			
		}	
	}
	/**
	 * 将数据{@code modelForView}填充到视图资源{@code viewResource}中，得到一个渲染后视图的视图结果
	 * @param viewResource 视图资源
	 * @param modelForView　视图所需的数据
	 * @return
	 */
	public String render(ViewResource viewResource,Map<String, Object> modelForView){
		return renderer.render(viewResource, modelForView);
	}
}
