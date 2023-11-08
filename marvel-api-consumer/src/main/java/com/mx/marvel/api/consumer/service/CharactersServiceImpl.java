package com.mx.marvel.api.consumer.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mx.marvel.api.consumer.constants.CharactersConstants;

/**Clase para el consumo de la API Marvel.
 * @author Max
 * @version 1.0.0
 */

@Service
public class CharactersServiceImpl implements CharactersService {
	
	@Value("${public.key.api}")
	private String publicKeyApi;
	
	@Value("${private.key.api}")
	private String privateKeyApi;
	
	@Autowired
	private WebClient webClient;
	
	 @Autowired
	 private CharactersFormatResponseService charactersFormatResponseService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Object> getCharacters(Integer characterId) throws NoSuchAlgorithmException {
		
		long startTime = System.currentTimeMillis();
		Instant timeStamp = Instant.now();
		String hash = getHash(timeStamp);
		String path = getPath(characterId);
		String jsonResponse = webClient.get()
				                .uri(uriBuilder -> uriBuilder
					                .path(path)
					                .queryParam(CharactersConstants.QUERY_PARAM_TIME_STAMP, timeStamp)
					                .queryParam(CharactersConstants.QUERY_PARAM_API_KEY, publicKeyApi)
					                .queryParam(CharactersConstants.QUERY_PARAM_HASH, hash)
					                .build())
				                .retrieve()
				                .bodyToMono(String.class)
				                .block();
	    
		return charactersFormatResponseService.responseOk(startTime, jsonResponse);
		
	}
	
	/**Método que crea un hash para el consumo de la API.
	 * @param timeStamp Instant: Marca de tiempo para crear la cadena hash, esta misma marca se envía como parámetro a la API (ts).
	 * @throws NoSuchAlgorithmException: Excepción al utilizar algoritmo de cifrado que no está disponible.
	 * @return hash String: Cadena que contiene el valor del hash resultado de timeStamp + privateKeyApi + publicKeyApi.
	 * @author MBL
	 */
	private String getHash(Instant timeStamp) throws NoSuchAlgorithmException {
		
		StringBuilder parametros = new StringBuilder();
		
		parametros.append(timeStamp);
		parametros.append(privateKeyApi);
		parametros.append(publicKeyApi);
		
		MessageDigest md = MessageDigest.getInstance(CharactersConstants.ALGORITHM_MD5);
		byte[] hash = md.digest(parametros.toString().getBytes());
		
		return DatatypeConverter.printHexBinary(hash).toLowerCase();
		
	} 
	
	/**Método que crea el path para consumir la API.
	 * @param characterId Integer: ID del Character a recuperar, solo si se recibe este parámetro se realizará la búsqueda filtrada,
	 * de lo contrario recuperará el catálogo completo.
	 * @return path String: Cadena que contiene el path para consumir la API.
	 * @author MBL
	 */
	private String getPath(Integer characterId) {
		
		StringBuilder path = new StringBuilder();
	
		path.append(CharactersConstants.PATH_CHARACTERS);
		
		if(characterId != null && characterId > 0) {
			
			path.append("/");
			path.append(characterId.toString());
			
		}
		
		return path.toString();
		
	}
	
}
