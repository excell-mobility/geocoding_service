package geocoding;

import geocodingapi.GeocodingService;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeocodingController {	
	
    @RequestMapping("/geocoding")
    public JSONObject geocoding(@RequestParam(value="address", defaultValue="''") String street,
    		@RequestParam(value="city", defaultValue="''") String city) throws JSONException, IOException, ParseException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getCoordinates(street, city).toString());
        return json;
    }
    
    @RequestMapping("/geocodingreverse")
    public org.json.simple.JSONObject geocodingreverse(@RequestParam(value="latitude", defaultValue="0.0") Double latitude,
    		@RequestParam(value="longitude", defaultValue="0.0") Double longitude) throws JSONException, IOException, ParseException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getStreet(latitude, longitude).toString()); 	
        return json;
    }
    
    @ExceptionHandler(value = Exception.class)
    public String inputParameterError() {
      return "Your input parameters for the geocoding service are invalid!";
    }
    
}