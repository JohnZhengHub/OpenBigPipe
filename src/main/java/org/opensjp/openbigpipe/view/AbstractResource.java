package org.opensjp.openbigpipe.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.opensjp.openbigpipe.exception.ViewResourceException;
import org.springframework.util.Assert;

public abstract class AbstractResource  implements Resource{
	private static final String FOLDER_SEPARATOR = "/";
	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
	
	private String relativePath;
	private String absolutePath;
	private InputStream inputStream;
	public AbstractResource(String path){
		/*String pathToUse = path.replace(WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}*/
		
		Assert.state(path != null, "resource path must not be null");
		this.relativePath = path;
	}
	
	@Override
	public InputStream getInputSteam() {
		if(inputStream != null)
			return inputStream;
		if(exist()){
			File file = new File(absolutePath);
			try {
				inputStream = new FileInputStream(file);
				return inputStream;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public String getRelativePath() {
		return relativePath;
	}
	@Override
	public String getAbsolutePath() {
		try{
			this.absolutePath = ViewResource.class.getClassLoader().getResource(relativePath).getPath();
		}catch(Exception e){
			throw new ViewResourceException("Resource path error: can't find "+relativePath);
		}
		return absolutePath;
	}
	
	@Override
	public boolean exist() {
		File file = new File(getAbsolutePath());
		return file.exists();
	}
	
	@Override
	public File getFile() throws IOException {
		throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path");
	}
}

