package geocoding.component;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import geocoding.connector.GeocodingConnector;
import geocoding.exceptions.InputParameterErrorException;


@Component
public class GeocodingService {
	
//	private final Logger log;
	
	@Autowired
	private GeocodingConnector geocodingConnector;
	
	private GeocodingExtraction extraction;
	
	public GeocodingService() {
//		log = LoggerFactory.getLogger(this.getClass());
		this.geocodingConnector = new GeocodingConnector();
		this.extraction = new GeocodingExtraction();
	}

	public JSONObject getCoordinates(String street, String city) throws JSONException, IOException, InputParameterErrorException {
		
		if(!street.matches("[a-zA-Z0-9ßéäöüÄÖÜ-]+\\p{Blank}*[a-zA-Z0-9ßéäöüÄÖÜ-]*\\p{Blank}+\\d+") 
				|| !city.matches("[a-zA-ZßéäöüÄÖÜ]+")) {
			throw new InputParameterErrorException("Your input parameters for the geocoding service are invalid!");
		}
		
		JSONArray coordinatesJson = geocodingConnector.getCoordinates(street, city);
		return extraction.extractCoordinates(coordinatesJson);
		
	}
	
	public JSONObject getStreet(Double latitude, Double longitude) throws JSONException, IOException, InputParameterErrorException {
		
		if(latitude.isNaN() || longitude.isNaN() || latitude == null || longitude == null
				|| latitude == 0 || longitude == 0) {
			throw new InputParameterErrorException("Your input parameters for the reverse "
					+ "geocoding service are invalid!");
		}
		
		JSONObject streetJson = geocodingConnector.getStreet(latitude, longitude);
		return extraction.extractAddress(streetJson);

	}
	
	// Version for Photon Geocoder
	public JSONObject getCoordinates(
			String city, 
			String street,
			String housenumber,
			String postcode
			) throws JSONException, IOException, InputParameterErrorException {
		
		if(!street.matches("[a-zA-Z0-9ßéäöüÄÖÜ-]+\\p{Blank}*[a-zA-Z0-9ßéäöüÄÖÜ-]*") 
				|| !city.matches("[a-zA-ZßéäöüÄÖÜ]+")
				|| !housenumber.matches("\\d+")
				|| !postcode.matches("\\d{5}")) {
			throw new InputParameterErrorException("Your input parameters for the geocoding service are invalid!");
		}
		
		JSONObject coordinatesJson = geocodingConnector.getPhotonCoordinates(city, street, housenumber, postcode);
		return extraction.extractPhotonCoordinates(coordinatesJson, street, housenumber, postcode);
		
	}
	
	public JSONObject getAddress(Double latitude, Double longitude) throws JSONException, IOException, InputParameterErrorException {
    	
		if(latitude.isNaN() || longitude.isNaN() || latitude == null || longitude == null
				|| latitude == 0 || longitude == 0) {
			throw new InputParameterErrorException("Your input parameters for the reverse "
					+ "geocoding service are invalid!");
		}
    	
		JSONObject streetJson = geocodingConnector.getPhotonAddress(latitude, longitude);
		return extraction.extractPhotonAddress(streetJson);

	}

}
