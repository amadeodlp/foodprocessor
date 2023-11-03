package com.example.foodprocessor.routing;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.example.foodprocessor.model.Caramel;
import com.example.foodprocessor.model.Pancake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.web.client.RestTemplate;

@Component
public class ApacheRouter extends RouteBuilder {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${api.doctor.url}")
    private String doctorApiUrl;
    
    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json);
        
        rest("/api/stomach")
            .post()
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .type(Caramel.class)
            .to("direct:caramelProcessing");
    
        rest("/api/doctor")
            .get()
            .produces("application/json")
            .to("direct:pancakeProcessing");
        
        from("direct:webhook")
            .choice()
            .when(body().isInstanceOf(Caramel.class))
            .to("direct:caramelProcessing")
            .when(body().isInstanceOf(Pancake.class))
            .to("direct:pancakeProcessing");
        
        from("direct:caramelProcessing")
            .to("rest:post:/api/stomach");
        
        from("direct:pancakeProcessing")
            .process(exchange -> {
                String doctorResponse = restTemplate.getForObject(doctorApiUrl, String.class);
                exchange.getIn().setBody(doctorResponse);
            });
    }
}