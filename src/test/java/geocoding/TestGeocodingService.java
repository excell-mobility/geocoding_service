package geocoding;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import geocoding.exceptions.InputParameterErrorException;
import geocoding.component.GeocodingService;

public class TestGeocodingService {
	
	private GeocodingService geocoding;
	
	@Before
	public void initialize() {
		geocoding = new GeocodingService();
	}

	@Test
	public void testGeocodingService() throws JSONException, IOException, InputParameterErrorException {
		
		JSONObject geocodingData = geocoding.getCoordinates("Würzburger Straße 45", "Dresden");
		assertTrue(geocodingData.has("longitude"));
		assertTrue(geocodingData.has("latitude"));
		
		geocodingData = geocoding.getStreet(51.03186715, 13.7126058064147);
		assertTrue(geocodingData.has("city"));
		assertTrue(geocodingData.has("street"));
		
		JSONObject geocodingPhotonData = geocoding.getCoordinates("Dresden", "Würzburger Straße", "45", "01187");
		assertTrue(geocodingPhotonData.has("longitude"));
		assertTrue(geocodingPhotonData.has("latitude"));
		
		geocodingPhotonData = geocoding.getAddress(51.03186715, 13.7126058064147);
		assertTrue(geocodingPhotonData.has("city"));
		assertTrue(geocodingPhotonData.has("street"));
		assertTrue(geocodingPhotonData.has("housenumber"));
		assertTrue(geocodingPhotonData.has("postcode"));
		
		geocodingPhotonData = geocoding.getCoordinates("Test 1", "Bla Bla");
		assertFalse(geocodingPhotonData.has("longitude"));
		assertFalse(geocodingPhotonData.has("latitude"));
		
	}

}