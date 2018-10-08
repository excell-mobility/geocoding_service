package geocoding.connector;

import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class GeocodingConnector {

	private HttpConnector connector;

	@Value("${url.geocoding.nominatim}")
	private String urlNominatim;

	@Value("${url.geocoding.photon}")
	private String urlPhoton;

	public GeocodingConnector() {
		this.connector = new HttpConnector();
	}

	public JSONArray getCoordinates(String street, String city)
			throws JSONException, IOException {
		
		String urlString = urlNominatim;
		urlString += "search?street=" + URLEncoder.encode(street, "UTF-8");
		urlString += "&city=" + URLEncoder.encode(city,"UTF-8");
		urlString += "&format=json";
		String result = this.connector.getConnectionString(urlString);
		return this.connector.getJSONArrayResult(result);
		
	}
	
	public JSONObject getStreet(Double latitude, Double longitude)
			throws JSONException, IOException {
		
		String urlString = urlNominatim;
		urlString += "reverse?format=json&lat=";
		urlString += latitude;
		urlString += "&lon=" + longitude;
		String result = this.connector.getConnectionString(urlString);
		return this.connector.getJSONObjectResult(result);
		
	}
	
	public JSONObject getPhotonCoordinates(
			String city, 
			String street,
			String housenumber,
			String postcode
			) throws JSONException, IOException {
		
		String urlString = urlPhoton;
		urlString += "api?q=" 
				+ URLEncoder.encode(city,"UTF-8") + ","
				+ URLEncoder.encode(postcode, "UTF-8") + ","
				+ URLEncoder.encode(street, "UTF-8")
				+ URLEncoder.encode(" ", "UTF-8")
				+ URLEncoder.encode(housenumber, "UTF-8");

		String result = this.connector.getConnectionString(urlString.toString());
		return this.connector.getJSONObjectResult(result);
		
	}
	
	public JSONObject getPhotonAddress(Double latitude, Double longitude)
			throws JSONException, IOException {
		
		String urlString = urlPhoton;
		urlString += "reverse?lon=" + longitude + "&lat=" + latitude;
		String result = this.connector.getConnectionString(urlString.toString());
		return this.connector.getJSONObjectResult(result);
	}

	
}