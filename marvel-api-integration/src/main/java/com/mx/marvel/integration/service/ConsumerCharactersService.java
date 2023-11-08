package com.mx.marvel.integration.service;

/**
 * @author MBL
 */

public interface ConsumerCharactersService {

	/**Método se utiliza para obtener la lista de personajes o los detalles de un personaje de Marvel por su ID.
	 * @param characterId El ID del personaje de Marvel que se desea obtener. Este debe ser un ID válido de un personaje de Marvel.
	 * @return Una cadena que representa los detalles del personaje de Marvel con el ID dado. La cadena está en formato JSON y 
	 * contiene los atributos del personaje, como su nombre, descripción y URL de la imagen del personaje.
	 * @author MBL
	 */
	String getCharacters(Integer characterId);
	
}
