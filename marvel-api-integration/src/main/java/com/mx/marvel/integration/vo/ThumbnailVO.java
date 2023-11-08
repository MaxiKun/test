package com.mx.marvel.integration.vo;

import java.io.Serializable;

/**
 * @author MBL
 */

public class ThumbnailVO implements Serializable{

	private static final long serialVersionUID = -7616508499984008114L;
	
	private String extension;
	private String path;
	
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
