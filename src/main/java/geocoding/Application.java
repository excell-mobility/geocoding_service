package geocoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import geocoding.component.GeocodingService;
import geocoding.controller.GeocodingController;
import springfox.documentation.service.ApiInfo;
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
          	//.apis(RequestHandlerSelectors.any()) 
          	//.paths(PathSelectors.any())
          .build()
          .genericModelSubstitutes(ResponseEntity.class)
          //.protocols(Sets.newHashSet("https"))
          .host("141.64.5.234/excell-geocoding-api")
          .apiInfo(apiInfo())
          ;
    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "ExCELL Geocoding API",
          "This API provides geocoding for addresses to coordinates and reverse.",
          "Version 1.0",
          "Use only for testing",
          "fkunde@beuth-hochschule",
          "Apache 2",
          "http://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}