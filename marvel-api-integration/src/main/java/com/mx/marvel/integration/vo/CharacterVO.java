package com.mx.marvel.integration.vo;

import java.io.Serializable;

/**
 * @author MBL
 */

public class CharacterVO implements Serializable{

	private static final long serialVersionUID = -2100966807462936631L;
	
	private long id; 
	private String description;
	private String name;
	private ThumbnailVO thumbnail;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the thumbnail
	 */
	public ThumbnailVO getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(ThumbnailVO thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}
