package geocoding.component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class GeocodingExtraction {
	
	//private final Logger log;
	
	public GeocodingExtraction() {
		//log = LoggerFactory.getLogger(this.getClass());
	}

	public JSONObject extractCoordinates(JSONArray coordinatesJson) {

		JSONObject obj = new JSONObject();
		if (coordinatesJson != null && !coordinatesJson.isNull(0)) {		
			JSONObject json = coordinatesJson.getJSONObject(0);
			Double latitude = json.getDouble("lat");
			Double longitude = json.getDouble("lon");
			obj.put("latitude", latitude);
			obj.put("longitude", longitude);
		}
		return obj;
		
	}
	
	public JSONObject extractAddress(JSONObject streetJson) {
		
		JSONObject obj = new JSONObject();
		if(streetJson != null && streetJson.has("address")) {
			JSONObject json = streetJson.getJSONObject("address");
			String street = json.getString("road");
			String house_number = "";
			if(json.has("house_number")) {
				house_number = json.getString("house_number");
			}
			String city = json.getString("city");
			if(house_number != null && house_number.length() > 0) {
				obj.put("street", street + " " + house_number);
			} else {
				obj.put("street", street);
			}
			obj.put("city", city);
		}
		return obj;
		
	}
	
	public JSONObject extractPhotonCoordinates(JSONObject input,
			String street, 
			String housenumber, 
			String postcode) throws UnsupportedEncodingException, JSONException {

		JSONObject obj = new JSONObject();
		JSONArray features = input.getJSONArray("features");
		if (features != null && features.length() > 0) {
			
			String searchedStreet = URLDecoder.decode(URLEncoder.encode(street,"UTF-8"),"UTF-8");
						
			for (int i=0;i<features.length();i++) {
				JSONArray coords = features.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
				JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
				
				if (properties.has("street") &&
						properties.has("housenumber") &&
						properties.has("postcode")) {
					if (URLDecoder.decode(
							URLEncoder.encode(
									properties.getString("street"),
									"UTF-8"),
							"UTF-8").toUpperCase().equalsIgnoreCase(searchedStreet.toUpperCase()) &&
							properties.getString("housenumber").equalsIgnoreCase(housenumber) &&
							properties.getString("postcode").equalsIgnoreCase(postcode)) {
						Double longitude = (Double) coords.get(0);
						Double latitude = (Double) coords.get(1);
						obj.put("latitude", latitude);
						obj.put("longitude", longitude);
						break;
					}
				}
			}
		}
		return obj;
	}

	public JSONObject extractPhotonAddress(JSONObject input) {
		
		JSONObject obj = new JSONObject();
		JSONArray features = input.getJSONArray("features");
		if (features != null && features.length() > 0) {
			JSONObject properties = features.getJSONObject(0).getJSONObject("properties");
			if(properties.has("name")) {
				obj.put("street", properties.getString("name"));
			} else if(properties.has("street")) {
				obj.put("street", properties.getString("street"));
			}
			if(properties.has("housenumber")) {
				obj.put("housenumber", properties.getString("housenumber"));
			}
			obj.put("city", properties.getString("city"));
			obj.put("postcode", properties.getString("postcode"));
		}
		
		return obj;
	}

}
