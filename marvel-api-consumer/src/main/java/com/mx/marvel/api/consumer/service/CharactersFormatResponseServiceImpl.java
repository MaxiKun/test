package com.mx.marvel.api.consumer.service;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mx.marvel.api.consumer.vo.CharactersResponseErrorVO;
import com.mx.marvel.api.consumer.vo.CharactersResponseOkVO;

/**
 * @author MBL
 */

@Service
public class CharactersFormatResponseServiceImpl implements CharactersFormatResponseService {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Object> responseOk(long startTime, String response){
		
		long elapsedTime = getElapsedTime(startTime); 
		HttpHeaders httpHeaders = getHttpHeaders();
		CharactersResponseOkVO responseOk = new CharactersResponseOkVO();
		JSONObject jsonObject = new JSONObject(response);
		
		responseOk.setElapsedTime(elapsedTime);
		responseOk.setData(jsonObject.toString());
				
		return new ResponseEntity<>(responseOk, httpHeaders, HttpStatus.OK);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Object> responseError(HttpStatus httpStatus, String response){
		 
		HttpHeaders httpHeaders = getHttpHeaders();
		CharactersResponseErrorVO responseError = new CharactersResponseErrorVO();
		
		responseError.setMessageError(response);
		
		return new ResponseEntity<>(responseError, httpHeaders, httpStatus);
		
	}
	
	/**Método que calcula el tiempo que se tardó en realizar el proceso.
	 * @param startTime long: Valor del tiempo en que inició el proceso.
	 * @return elapsedTime long: Valor del tiempo total que tardó el proceso.
	 * @author MBL
	 */
	private long getElapsedTime(long startTime) {
		
		long endTime = System.currentTimeMillis();
		
		return (endTime - startTime);  
		
	}
	
	/**Método que obtiene la configuración del objeto HttpHeaders para establecer por defecto
	 * application/json;charset=utf-8
	 * @return httpHeaders HttpHeaders: Objeto con la configuración estandarizada.
	 * @author MBL 
	 */
	private HttpHeaders getHttpHeaders() {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.toString());
		
		return httpHeaders;
		
	}
	
}
