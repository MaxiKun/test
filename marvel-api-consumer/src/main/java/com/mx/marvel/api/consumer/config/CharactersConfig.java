package com.mx.marvel.api.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author MBL
 * pruebas 
 */

@Configuration
public class CharactersConfig {

	@Value("${url.base.api}")
	private String baseUrl;
	
	/**Método que configura y crea un objeto WebClient, que se puede usar para realizar solicitudes HTTP.
	 * @return Un objeto WebClient configurado con una cabecera por defecto que acepta JSON y una URL base establecida.
	 * @author MBL
	 */
    @Bean
    public WebClient webClient() {
    	
        return WebClient.builder()
                		.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                		.baseUrl(baseUrl)
                		.build();

    }
	
}
