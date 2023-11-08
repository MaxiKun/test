package com.mx.marvel.api.consumer.vo;

import java.io.Serializable;

/**
 * @author MBL
 */

public class CharactersResponseErrorVO implements Serializable{
	
	private static final long serialVersionUID = 7697638055466775473L;
	
	private String messageError;

	/**
	 * @return the messageError
	 */
	public String getMessageError() {
		return messageError;
	}

	/**
	 * @param messageError the messageError to set
	 */
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	
}
