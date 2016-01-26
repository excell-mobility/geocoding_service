package geocodingapi;

import io.swagger.annotations.ApiModelProperty;

public class ReverseGeocodingResponse {

	private String city;
	private String street;
	private String housenumber;
	private String postcode;
	
	public ReverseGeocodingResponse(String city,
			String street,
			String housenumber,
			String postcode) {
		this.city = city;
		this.street = street;
		this.housenumber = housenumber;
		this.postcode = postcode;
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

	@ApiModelProperty(dataType = "string")
	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	@ApiModelProperty(dataType = "string")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
