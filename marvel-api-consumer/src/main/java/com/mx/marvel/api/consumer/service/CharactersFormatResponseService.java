package com.mx.marvel.api.consumer.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author MBL
 */

public interface CharactersFormatResponseService {

	/**Método que aplica formato a la respuesta cuando es exitosa (200).
	 * @param startTime long: Valor del tiempo en que inició el proceso.
	 * @param response String: Cadena que contiene el JSON obtenido al consumir la API.
	 * @return responseEntity ResponseEntity<Object>: Objeto que contiene la respuesta exitosa con formato.
	 * @author MBL
	 */
	ResponseEntity<Object> responseOk(long startTime, String response);
	
	/**Método que aplica formato a la respuesta cuando es diferente a exitosa (200).
	 * @param httpStatus HttpStatus: Objeto que contiene el valor del HttpStatus.
	 * @param response String: Cadena que contiene detalle del error.
	 * @return responseEntity ResponseEntity<Object>: Objeto que contiene la respuesta con formato.
	 * @author MBL
	 */
	ResponseEntity<Object> responseError(HttpStatus httpStatus, String response);
	
}
