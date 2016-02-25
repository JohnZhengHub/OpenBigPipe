package org.opensjp.openbigpipe.view;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.opensjp.openbigpipe.exception.RenderException;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;

/**
 * Freemarker的视图渲染器
 * @author John Zheng
 *
 */
public class FreemarkerRenderer implements ViewRenderer{
	private Configuration cfg;
	public FreemarkerRenderer(){
		init();
	}
    private void init() {
        cfg = new freemarker.template.Configuration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringLoader);
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        cfg.setTemplateUpdateDelay(600000);
        cfg.setNumberFormat("#");
        cfg.setClassicCompatible(true);
        cfg.setDefaultEncoding("UTF-8");
    }
    
	@Override
	public String render(ViewResource viewResource, Map<String, Object> modelForView) {
		String renderResult = "";
		if(!(viewResource instanceof FreemarkerViewResource)){
			throw new RenderException("Freemarker renderer can't render view which isn't freemarker file:"
					+" Error occur in render "+viewResource.getRelativePath());
		}
        try {
        	Template template = new Template(viewResource.getContent(), 
        			new StringReader(viewResource.getContent()), cfg);
            StringWriter writer = new StringWriter();
            template.process(modelForView, writer);
            renderResult = writer.toString();
            
        } catch (Exception e) {
            throw new RenderException(e);
        }
        return renderResult;
	}

}
