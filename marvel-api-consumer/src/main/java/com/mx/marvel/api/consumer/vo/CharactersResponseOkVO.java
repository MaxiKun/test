package com.mx.marvel.api.consumer.vo;

import java.io.Serializable;

/**
 * @author MBL
 */

public class CharactersResponseOkVO implements Serializable{
	
	private static final long serialVersionUID = 412721928275406722L;
	
	private long elapsedTime;
	private String data;
	
	/**
	 * @return the elapsedTime
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}
	/**
	 * @param elapsedTime the elapsedTime to set
	 */
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
}
