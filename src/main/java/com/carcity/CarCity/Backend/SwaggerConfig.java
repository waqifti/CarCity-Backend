package com.carcity.CarCity.Backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig  {   
	
	/*@Bean
    public Docket swaggerSettingsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Settings")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xyz"))
                .paths(regex("/secure/api/v1/settings/.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("Settings API").build())
                .globalOperationParameters(operationParameters());
    }*/
	
	
    @Bean
    public Docket api() { 
    	return new Docket(DocumentationType.SWAGGER_2)
    			 .groupName("External Partners")
    	         .select()                                  
    	          .apis(RequestHandlerSelectors.basePackage("com.carcity.CarCity.Backend.RestContollers")  )        //RequestHandlerSelectors.any()   
    	          .paths(PathSelectors.any())                          
    	          .build().apiInfo(api1info());  
        
       
    }
    
    /*@Bean
    public Docket api2() { 
    	

    	
    	return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("Internal")
    	         .select()                                  
    	         .apis(RequestHandlerSelectors.basePackage("monami.lms.webclientrestcontollers"))      
    	         
    	          .paths(PathSelectors.any())                          
    	          .build().apiInfo(api2info());  
        
       
    }*/
    

   	private ApiInfo api1info() {

   		return new ApiInfoBuilder().title("Track")
   				.description("Track")
   				.contact(new Contact("Iftekhar Ahmed", "author", "iftekhar@nullbrainer.io"))
   				.license("Track All rights reserved")
   				.version("1.0.0")
   				.build();
   	}
   	
	
	

    
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}
