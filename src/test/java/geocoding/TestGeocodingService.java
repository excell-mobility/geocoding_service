package geocoding;

import static org.junit.Assert.*;

import java.io.IOException;

import geocodingapi.GeocodingService;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class TestGeocodingService {
	
	private GeocodingService geocoding;
	
	@Before
	public void initialize() {
		geocoding = new GeocodingService();
	}

	@Test
	public void testGeocodingService() throws JSONException, IOException {
		
		JSONObject geocodingData = geocoding.getCoordinates("Würzburger Straße 45", "Dresden");
		assertTrue(geocodingData.has("longitude"));
		assertTrue(geocodingData.has("latitude"));
		
		geocodingData = geocoding.getStreet(51.03186715, 13.7126058064147);
		assertTrue(geocodingData.has("city"));
		assertTrue(geocodingData.has("street"));
		
	}

}