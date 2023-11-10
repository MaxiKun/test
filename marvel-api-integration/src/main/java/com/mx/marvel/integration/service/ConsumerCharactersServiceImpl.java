package com.mx.marvel.integration.service;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.marvel.api.consumer.service.CharactersService;
import com.mx.marvel.api.consumer.vo.CharactersResponseOkVO;
import com.mx.marvel.integration.constants.ConsumerCharactersConstants;
import com.mx.marvel.integration.repository.BitacoraAccesoMarvelEntity;
import com.mx.marvel.integration.repository.BitacoraAccesoMarvelRepository;
import com.mx.marvel.integration.vo.CharacterVO;

/**
 * @author MBL
 *
 */

@Service
public class ConsumerCharactersServiceImpl implements ConsumerCharactersService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerCharactersServiceImpl.class);

	@Autowired
	private CharactersService charactersService;
	
	@Autowired
	private BitacoraAccesoMarvelRepository bitacoraRepository;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCharacters(Integer characterId) {
		
		saveBitacora(characterId);
		
		ResponseEntity<Object> responseMarvel = getCharactersApi(characterId);
		
		return processResponseApi(responseMarvel);
		
	}
	
	/**Método que se utiliza para procesar la respuesta obtenida de la API de Marvel.
	 *
	 * @param responseMarvel Un objeto ResponseEntity que contiene la respuesta de la API de Marvel. 
	 *                       Este objeto puede contener los datos de los personajes o información de error, dependiendo de la respuesta de la API.
	 * @return Una cadena de texto que representa el resultado del procesamiento de la respuesta de la API. 
	 *         Esta cadena puede contener los datos de los personajes en un formato específico, o un mensaje de error si la respuesta de la API indicó un error.
	 * @author MBL
	 */
	private String processResponseApi(ResponseEntity<Object> responseMarvel){

		JSONObject response = null;

		if(responseMarvel != null) {
			
			boolean repuestaApiCorrecta = (responseMarvel.getStatusCode() == HttpStatus.OK);

			if(repuestaApiCorrecta && responseMarvel.getBody() instanceof CharactersResponseOkVO responseOk) {
				
				responseOk = (CharactersResponseOkVO) responseMarvel.getBody();
				
				if(responseOk != null && responseOk.getData() != null) {
					
					String jsonResponseMarvel = responseOk.getData();
					JSONObject dataMarvel = new JSONObject(jsonResponseMarvel);
					boolean conResultados = (dataMarvel.has(ConsumerCharactersConstants.KEY_DATA) && 
											 dataMarvel.get(ConsumerCharactersConstants.KEY_DATA) != JSONObject.NULL && 
											 dataMarvel.getJSONObject(ConsumerCharactersConstants.KEY_DATA).has(ConsumerCharactersConstants.KEY_RESULTS) &&
											 dataMarvel.getJSONObject(ConsumerCharactersConstants.KEY_DATA).get(ConsumerCharactersConstants.KEY_RESULTS) != JSONObject.NULL); 
					
					if(conResultados && dataMarvel.getJSONObject(ConsumerCharactersConstants.KEY_DATA)
												  .get(ConsumerCharactersConstants.KEY_RESULTS) instanceof JSONArray arrayResultados) {
						
						response = setDataCharacters(arrayResultados);
						
					}
					
				}
				
			}
	
		}

		if(response == null){
			
			response = new JSONObject();
			response.put(ConsumerCharactersConstants.KEY_STATUS, HttpStatus.NOT_FOUND.value());

		}

		return response.toString();

	}
	
	/**Método que se utiliza para establecer los datos de los personajes en un JSONObject a partir de un JSONArray de resultados.
	 *
	 * @param arrayResultados Un JSONArray que contiene los resultados obtenidos de la API. 
	 *                        Este array contiene objetos JSON que representan a los personajes.
	 * @return Un JSONObject que contiene los datos de los personajes. 
	 *         Cada clave en este JSONObject es un atributo de los personajes, 
	 *         y el valor asociado es el valor de ese atributo para los personajes en arrayResultados.
	 * @author MBL
	 */
	private JSONObject setDataCharacters(JSONArray arrayResultados) {
		
		JSONObject response = null;
		
		if(arrayResultados != null && !arrayResultados.isEmpty()) {
			
			JSONArray arrayCharacters = new JSONArray();
			JSONObject character;
			CharacterVO characterVO;
			
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			for (int indice = 0; indice < arrayResultados.length(); indice++) {
				
				character = (JSONObject) arrayResultados.get(indice); 
				
				try {
					
					characterVO = objectMapper.readValue(character.toString(), CharacterVO.class);
					arrayCharacters.put(new JSONObject(objectMapper.writeValueAsString(characterVO)));
					
				} catch (JSONException e) {
					
					LOGGER.error("Error al establecer los datos de los personajes - JSONException", e);
					
				} catch (Exception e) {
					
					LOGGER.error("Error al establecer los datos de los personajes - Exception", e);
					
				}
				
			}
			
			if(!arrayCharacters.isEmpty()) {
				
				response = new JSONObject();
				
				response.put(ConsumerCharactersConstants.KEY_STATUS, HttpStatus.OK.value());
				response.put(ConsumerCharactersConstants.KEY_ARRAY_CHARACTERS, arrayCharacters);
				
			}
			
		}

		return response;
		
	}
	
	/**Método se utiliza para obtener los personajes de la API externa.
	 * @param characterId El ID del personaje que se desea obtener. Este es un entero que representa el identificador único del personaje.
	 * @return Un objeto ResponseEntity que contiene el resultado de la llamada a la API. 
	 *         Si la llamada fue exitosa, este objeto contendrá los datos del personaje. 
	 *         Si la llamada falló, este objeto contendrá información sobre el error.
	 * @autor MBL
	 */
	private ResponseEntity<Object> getCharactersApi(Integer characterId) {
		
		ResponseEntity<Object> responseMarvel = null;
		
		try {
			
			responseMarvel = charactersService.getCharacters(characterId);
			
		} catch (NoSuchAlgorithmException e) {
 	
			LOGGER.error("Error al obtener los datos de la API", e);

		}
		
		return responseMarvel;
		
	} 
	
	/**Este método se utiliza para guardar un registro en la bitácora de acceso a los personajes de Marvel.
	 * @param characterId El ID del personaje de Marvel que se accedió. Este ID se guardará en la bitácora 
	 * para llevar un registro de los personajes a los que se ha accedido.
	 * @author MBL
	 */
	private void saveBitacora(Integer characterId) {
		
		try {
			
			BitacoraAccesoMarvelEntity bitacoraEntity = new BitacoraAccesoMarvelEntity();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			
			bitacoraEntity.setHoraConsulta(sdf.format(calendar.getTime()));
			
			if(characterId != null) {
				
				bitacoraEntity.setPath(ConsumerCharactersConstants.DESCRIPTION_PATH_TWO);
				
			}else {
				
				bitacoraEntity.setPath(ConsumerCharactersConstants.DESCRIPTION_PATH_ONE);
				
			}
			
			bitacoraRepository.save(bitacoraEntity);
			
		} catch (Exception e) {
			
			LOGGER.error("No se logró guardar el registro en la bitácora", e);
			
		}
		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BitacoraAccesoMarvelEntity> getDataBitacora() {
		
		return (List<BitacoraAccesoMarvelEntity>) bitacoraRepository.findAll();
		
	} 
	
}
