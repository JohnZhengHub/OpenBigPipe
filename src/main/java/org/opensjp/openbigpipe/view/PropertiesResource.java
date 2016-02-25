package org.opensjp.openbigpipe.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** 
 * properties 配置文件资源
 * @author John Zheng
 *
 */
public class PropertiesResource extends AbstractResource{
	private Properties props;
	
	public PropertiesResource(String viewPath) {
		super(viewPath); 
		init();
	}	
	private void init(){
		InputStream in = null;  
        try {
			in = new FileInputStream(this.getAbsolutePath()); 
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {  
            if (null != in)
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        } 
	}
	/**
	 * 获取值
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getValue(String key){
		String value = null;
		if(props  != null && key != null)
			value = props.getProperty(key);  
        return  value;
    }
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}  
}
