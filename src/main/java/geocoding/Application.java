package geocoding;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import geocoding.component.GeocodingService;
import geocoding.connector.GeocodingConnector;
import geocoding.controller.GeocodingController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
		GeocodingController.class,
		GeocodingService.class,
		GeocodingConnector.class
	})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Docket geocodingApi(ServletContext servletContext) { 
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("excell-geocoding-api")
          .select()
          .apis(RequestHandlerSelectors.any()) 
          .paths(Predicates.not(PathSelectors.regex("/error")))
          .paths(Predicates.not(PathSelectors.regex("/health")))
          .paths(Predicates.not(PathSelectors.regex("/health.json")))
          .build()
          .genericModelSubstitutes(ResponseEntity.class)
          .host("localhost:34343")
          .apiInfo(apiInfo());
          /*.protocols(Sets.newHashSet("https"))
          .host("prev.excell-mobility.de")
          .securitySchemes(Lists.newArrayList(apiKey()))
          .securityContexts(Lists.newArrayList(securityContext()))
          .apiInfo(apiInfo())
          .pathProvider(new RelativePathProvider(servletContext) {
                @Override
                public String getApplicationBasePath() {
                    return "/integration/api/v1/service-request/geocodingservice";
                }
            });*/
    }
    
	private ApiKey apiKey() {
		return new ApiKey("api_key", "Authorization", "header");
	}
	
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/*.*"))
            .build();
    }

    private List<SecurityReference> defaultAuth() {
    	List<SecurityReference> ls = new ArrayList<>();
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope[0];
    	SecurityReference s = new SecurityReference("api_key", authorizationScopes);
    	ls.add(s);
    	return ls;
    }

	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Token", ApiKeyVehicle.HEADER, "Authorization", ",");
	}
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "ExCELL Geocoding API",
          "Die Geocoding API bietet je zwei Endpunkte (Wrapper um die bekannten OpenStreetMap Geocoder Nominatim und Photon),"
          + " um Text-basierte Adressen in GPS Koordinaten umzuwandelnd und umgekehrt."
          + " Geocoding wird benötigt wenn z.B. aus Kundenadressen Zielkoordinaten für das Routing erstellt werden müssen.\n\n"
          + "The Geocoding API provides two endpoints (wrapper for the well-known OpenStreetMap geocoder Nominatim and Photon)"
          + " to transform text-based addresses into GPS coordinates and vice versa."
          + " Geocoding is necessary e.g. for converting a client address into a routing destination.\n",
          "1.0",
          "Use only for testing",
          new Contact(
        		  "Beuth Hochschule für Technik Berlin - Labor für Rechner- und Informationssysteme - MAGDa Gruppe",
        		  "https://projekt.beuth-hochschule.de/magda/poeple",
        		  "fkunde@beuth-hochschule"),
          "Link to source code",
          "https://github.com/excell-mobility/geocoding_service",
          new ArrayList<VendorExtension>());
        return apiInfo;
    }

}