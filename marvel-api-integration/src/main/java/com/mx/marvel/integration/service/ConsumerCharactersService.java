package com.mx.marvel.integration.service;

import java.util.List;

import com.mx.marvel.integration.repository.BitacoraAccesoMarvelEntity;

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
	
	/**Este método se utiliza para obtener todos los registros de la bitácora de acceso a la API de Marvel.
	 * @return Una lista de entidades BitacoraAccesoMarvelEntity que representan los registros de la bitácora de acceso.
	 */
	List<BitacoraAccesoMarvelEntity> getDataBitacora();
	
}
