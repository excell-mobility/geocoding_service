package geocoding.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import geocoding.exceptions.InputParameterErrorException;
import geocoding.component.GeocodingService;
import geocoding.model.GeoPoint;
import geocoding.model.ReverseGeocodingResponse;
import geocoding.model.ReverseGeocodingResponseNominatim;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*")
@RestController
public class GeocodingController {

	@Autowired
	GeocodingService geocodingService;
	
    @RequestMapping(value = "/v1/nominatim/geocoding", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Get lat/lon coordinates from address", 
    		response=GeoPoint.class,
    		produces = "application/json")
    public JSONObject geocoding(
    		@ApiParam(name="address", value="Address (Street + Housenumber)") 
    		@RequestParam(value="address") String street,
    		@ApiParam(name="city", value="City") 
    		@RequestParam(value="city") String city
    		) throws JSONException, IOException, ParseException, InputParameterErrorException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getCoordinates(street, city).toString());
        return json;
    }
    
    @RequestMapping(value = "/v1/nominatim/reversegeocoding", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Get address from lat/lon coordinates",
    		response=ReverseGeocodingResponseNominatim.class,
    		produces = "application/json")
    public org.json.simple.JSONObject reverseGeocodingN(
    		@ApiParam(name="latitude", value="Latitude of position") 
    		@RequestParam(value="latitude") Double latitude,
    		@ApiParam(name="longitude", value="Longitude of position") 
    		@RequestParam(value="longitude") Double longitude
    		) throws JSONException, IOException, ParseException, InputParameterErrorException {
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
    		@ApiParam(name="city", value="City") 
    		@RequestParam(value="city") String city,
    		@ApiParam(name="street", value="Street") 
    		@RequestParam(value="street") String street,
    		@ApiParam(name="housenumber", value="Housenumber") 
    		@RequestParam(value="housenumber") String housenumber,
    		@ApiParam(name="postcode", value="Postcode") 
    		@RequestParam(value="postcode") String postcode
    		) throws JSONException, IOException, ParseException, InputParameterErrorException {
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
    		@ApiParam(name="latitude", value="Latitude of position") 
    		@RequestParam(value="latitude") Double latitude,
    		@ApiParam(name="longitude", value="Longitude of position") 
    		@RequestParam(value="longitude") Double longitude
    		) throws JSONException, IOException, ParseException, InputParameterErrorException {
    	JSONObject json = (JSONObject) new JSONParser().
    			parse(new GeocodingService().getAddress(latitude, longitude).toString()); 	
        return json;
    }
    
    @ExceptionHandler(value = InputParameterErrorException.class)
    public BodyBuilder geocodingParameterError() {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }
   
}