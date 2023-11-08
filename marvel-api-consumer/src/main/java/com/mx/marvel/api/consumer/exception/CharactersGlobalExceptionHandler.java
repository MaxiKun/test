package com.mx.marvel.api.consumer.exception;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mx.marvel.api.consumer.constants.CharactersConstants;
import com.mx.marvel.api.consumer.service.CharactersFormatResponseService;

/**
 * @author MBL
 *
 */

@ControllerAdvice
public class CharactersGlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CharactersFormatResponseService charactersFormatResponseService;
	
	/**Método maneja las excepciones de tipo NoSuchAlgorithmException que pueden ocurrir en la aplicación.
	 * @param ex La excepción NoSuchAlgorithmException que se ha lanzado. Esta excepción ocurre cuando se 
	 * solicita un algoritmo de cifrado que no está disponible en el entorno.
	 * @return Un objeto ResponseEntity que contiene el error formateado y el estado HTTP. 
	 * El estado HTTP será siempre INTERNAL_SERVER_ERROR (500), y el cuerpo de la respuesta será un mensaje 
	 * de error que indica que se solicitó un algoritmo de cifrado no disponible.
	 * @author MBL
	 */
	@ExceptionHandler(NoSuchAlgorithmException.class)
	public ResponseEntity<Object> handleMyException(NoSuchAlgorithmException ex) {
		
		String error = messageSource.getMessage(CharactersConstants.ERROR_MESSAGE_DIGEST_ALGORITHM, null, LocaleContextHolder.getLocale());
		
		return charactersFormatResponseService.responseError(HttpStatus.INTERNAL_SERVER_ERROR, error);
		
	}
	
	/**Método maneja todas las excepciones no capturadas específicamente por otros manejadores de excepciones en la aplicación.
	 * @param ex La excepción que se ha lanzado. Esta es una excepción general que puede representar cualquier tipo de error 
	 * que ocurra durante la ejecución de la aplicación.
	 * @return Un objeto ResponseEntity que contiene el error formateado y el estado HTTP. 
	 * El estado HTTP será siempre INTERNAL_SERVER_ERROR (500), y el cuerpo de la respuesta será un mensaje de error general que 
	 * indica que ocurrió un error durante la ejecución de la aplicación.
	 * @author MBL
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleMyException(Exception ex) {
		
		String error = messageSource.getMessage(CharactersConstants.ERROR_GENERAL, null, LocaleContextHolder.getLocale());
		
		return charactersFormatResponseService.responseError(HttpStatus.INTERNAL_SERVER_ERROR, error);
		
	}
	
}