package geocoding.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import beans.GeoPoint;
import geocoding.component.GeocodingService;
import geocoding.model.ReverseGeocodingResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*")
@RestController
public class GeocodingController {

	GeocodingService geocodingService;
	
    @RequestMapping(value = "/v1/nominatim/geocoding", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Get lat/lon coordinates from address", 
    		response=GeoPoint.class,
    		produces = "application/json")
    public JSONObject geocoding(
    		@ApiParam(name="address", value="Address (Street + Housenumber)", defaultValue="''") 
    		@RequestParam(value="address", defaultValue="''") String street,
    		@ApiParam(name="city", value="City", defaultValue="''") 
    		@RequestParam(value="city", defaultValue="''") String city
    		) throws JSONException, IOException, ParseException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getCoordinates(street, city).toString());
        return json;
    }
    
    @RequestMapping(value = "/v1/nominatim/reversegeocoding", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Get address from lat/lon coordinates",
    		produces = "application/json")
    public org.json.simple.JSONObject reverseGeocodingN(
    		@ApiParam(name="latitude", value="Latitude of position", defaultValue="0.0") 
    		@RequestParam(value="latitude", defaultValue="0.0") Double latitude,
    		@ApiParam(name="longitude", value="Longitude of position", defaultValue="0.0") 
    		@RequestParam(value="longitude", defaultValue="0.0") Double longitude
    		) throws JSONException, IOException, ParseException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getStreet(latitude, longitude).toString()); 	
        return json;
    }
	
    @RequestMapping(value = "/v1/photon/geocoding", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Get lat/lon coordinates from address", 
    		response=GeoPoint.class,
    		produces = "application/json")
    public JSONObject geocoding(
    		@ApiParam(name="city", value="City", defaultValue="''") 
    		@RequestParam(value="city", defaultValue="''") String city,
    		@ApiParam(name="street", value="Street", defaultValue="''") 
    		@RequestParam(value="street", defaultValue="''") String street,
    		@ApiParam(name="housenumber", value="Housenumber", defaultValue="''") 
    		@RequestParam(value="housenumber", defaultValue="''") String housenumber,
    		@ApiParam(name="postcode", value="Postcode", defaultValue="''") 
    		@RequestParam(value="postcode", defaultValue="''") String postcode
    		) throws JSONException, IOException, ParseException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getCoordinates(city,street,housenumber,postcode).toString());
        return json;
    }
    
    @RequestMapping(value = "/v1/photon/reversegeocoding", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Get address from lat/lon coordinates", 
    		response=ReverseGeocodingResponse.class,
    		produces = "application/json")
    public org.json.simple.JSONObject reverseGeocodingP(
    		@ApiParam(name="latitude", value="Latitude of position", defaultValue="0.0") 
    		@RequestParam(value="latitude", defaultValue="0.0") Double latitude,
    		@ApiParam(name="longitude", value="Longitude of position", defaultValue="0.0") 
    		@RequestParam(value="longitude", defaultValue="0.0") Double longitude
    		) throws JSONException, IOException, ParseException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getAddress(latitude, longitude).toString()); 	
        return json;
    }
    
    /*@ExceptionHandler(value = Exception.class)
    public String inputParameterError() {
      return "Your input parameters for the geocoding service are invalid!";
    }
    */
}