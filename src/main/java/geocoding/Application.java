package geocoding;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Predicates;

import geocoding.component.GeocodingService;
import geocoding.controller.GeocodingController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
		GeocodingController.class,
		GeocodingService.class
	})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Docket geocodingApi() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("excell-geocoding-api")
          .select()
          .apis(RequestHandlerSelectors.any()) 
          .paths(Predicates.not(PathSelectors.regex("/error")))
          .paths(Predicates.not(PathSelectors.regex("/health")))
          .paths(Predicates.not(PathSelectors.regex("/health.json")))
          .build()
          .genericModelSubstitutes(ResponseEntity.class)
          //.protocols(Sets.newHashSet("https"))
//          .host("localhost:34343")
          .host("141.64.5.234/excell-geocoding-api")
          .apiInfo(apiInfo())
          ;
    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "ExCELL Geocoding API",
          "This API provides geocoding for addresses to GPS coordinates and reverse geocoding. "
          + "For example, the geocoding API is required to transform appointment addresses to GPS coordinates.",
          "Version 1.0",
          "Use only for testing",
          new Contact(
        		  "Felix Kunde, Stephan Pieper",
        		  "https://projekt.beuth-hochschule.de/magda/poeple",
        		  "fkunde@beuth-hochschule"),
          "Apache 2",
          "http://www.apache.org/licenses/LICENSE-2.0",
          new ArrayList<VendorExtension>());
        return apiInfo;
    }

}