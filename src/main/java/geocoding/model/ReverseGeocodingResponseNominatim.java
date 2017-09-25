package geocoding.model;

import io.swagger.annotations.ApiModelProperty;

public class ReverseGeocodingResponseNominatim {

	private String city;
	private String street;
	
	public ReverseGeocodingResponseNominatim(String city,
			String street,
			String housenumber,
			String postcode) {
		this.city = city;
		this.street = street;
	}
	
	@ApiModelProperty(dataType = "string")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@ApiModelProperty(dataType = "string")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
