package geocodingapi;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rest.GeocodingConnector;
import extraction.GeocodingExtraction;

public class GeocodingService {
	
	private GeocodingExtraction extraction;

	public GeocodingService() {
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

}
