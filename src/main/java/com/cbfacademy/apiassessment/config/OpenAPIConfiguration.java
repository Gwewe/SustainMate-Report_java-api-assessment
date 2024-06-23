package com.cbfacademy.apiassessment.config;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi(){
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Production Environment");

        Contact myDeveloperContact = new Contact();
        myDeveloperContact.setName("Wedna G.");
        myDeveloperContact.setEmail("wedna.guirand@gmail.com");

        Info information = new Info();
        information.title("SustainMate Report API");
        information.version("1.0");
        information.description("API used to fetch and manage reports regarding sustainability in the UK.");
        information.contact(myDeveloperContact);

        return new OpenAPI().info(information).servers(List.of(server));
    }

}
