package com.example.foodprocessor.messaging;

import com.example.foodprocessor.exceptions.TheOnlyException;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookListener {
    
    @Autowired
    private CamelContext camelContext;
    
    @PostMapping("/webhook/receive")
    public void receiveWebhook(@RequestBody String message) throws TheOnlyException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody("direct:webhook", message);
        } catch (Exception e) {
            throw new TheOnlyException("Cannot can");
        }
    }
}