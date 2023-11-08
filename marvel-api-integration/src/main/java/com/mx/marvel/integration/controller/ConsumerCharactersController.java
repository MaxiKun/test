package com.mx.marvel.integration.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mx.marvel.integration.service.ConsumerCharactersService;

/**
 * @author MBL
 */

@RestController
@RequestMapping(value = "consumer/api/marvel")
public class ConsumerCharactersController {
	
	@Autowired
	private ConsumerCharactersService consumerCharactersService;
	
	/**Método que obtiene los Characters al consumir API de Marvel.
	 * @param characterId Integer: ID del Character a recuperar, solo si se recibe este parámetro se realizará la búsqueda filtrada,
	 * de lo contrario recuperará el catálogo completo.
	 * @return responseEntity ResponseEntity<Object>: Objeto que contiene la respuesta exitosa con formato.
	 * @author MBL
	 */
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "getCharacters", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getCharacters(@RequestParam(value = "characterId", required = false) Integer characterId){
		 
		return consumerCharactersService.getCharacters(characterId);
		 
	} 

}
