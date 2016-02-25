package org.opensjp.openbigpipe.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.opensjp.openbigpipe.exception.ViewResourceException;

/**
 *是所有视图的抽象，如jsp,freemarker等视图.
 * @author john Zheng
 *
 */
public abstract class ViewResource extends AbstractResource{
	/** freemarker 资源 **/
	public static final String FREEMARKER_TYPE = "ftl";
	/**  JSP 视图资源 **/
	public static final String JSP_TYPE = "jsp";
	
	public ViewResource(String viewPath){
		super(viewPath);
	}
	public String getContent(){
		File file = new File(this.getAbsolutePath());
		StringBuilder contentBuilder = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str;
			while ((str = br.readLine()) != null) {//使用readLine方法，一次读一行
				contentBuilder.append(str + "\n");
			}	
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return contentBuilder.toString();
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 获取视图资源的类型
	 * @return
	 */
	public abstract String getViewType();
}
