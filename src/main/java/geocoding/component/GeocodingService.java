package geocoding.component;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import extraction.GeocodingExtraction;
import rest.GeocodingConnector;

@Component
public class GeocodingService {
	
	private final Logger log;
	private GeocodingExtraction extraction;
	
	public GeocodingService() {
		log = LoggerFactory.getLogger(this.getClass());
		this.extraction = new GeocodingExtraction();
	}

	public JSONObject getCoordinates(String street, String city) throws JSONException, IOException {
		
		JSONArray coordinatesJson = GeocodingConnector.getCoordinates(street, city);
		return extraction.extractCoordinates(coordinatesJson);
		
	}
	
	public JSONObject getStreet(Double latitude, Double longitude) throws JSONException, IOException {
		
		JSONObject streetJson = GeocodingConnector.getStreet(latitude, longitude);
		return extraction.extractAddress(streetJson);

	}
	
	// Version for Photon Geocoder
	public JSONObject getCoordinates(
			String city, 
			String street,
			String housenumber,
			String postcode
			) throws JSONException, IOException {
		
		log.debug("IN - city: " + city);
    	log.debug("IN - street: " + street);
    	log.debug("IN - housenumber: " + housenumber);
    	log.debug("IN - postcode: " + postcode);
		
		JSONObject coordinatesJson = GeocodingConnector.getPhotonCoordinates(city, street, housenumber, postcode);
		return extraction.extractPhotonCoordinates(coordinatesJson, street, housenumber, postcode);
		
	}
	
	public JSONObject getAddress(Double latitude, Double longitude) throws JSONException, IOException {
		
		log.debug("IN - latitude: " + latitude);
    	log.debug("IN - longitude: " + longitude);
    	
		JSONObject streetJson = GeocodingConnector.getPhotonAddress(latitude, longitude);
		return extraction.extractPhotonAddress(streetJson);

	}

}
