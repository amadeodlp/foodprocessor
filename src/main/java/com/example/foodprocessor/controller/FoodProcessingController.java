package com.example.foodprocessor.controller;

import com.example.foodprocessor.database.DynamoDBService;
import com.example.foodprocessor.exceptions.TheOnlyException;
import com.example.foodprocessor.model.Caramel;
import com.example.foodprocessor.model.Pancake;
import com.example.foodprocessor.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

public class FoodProcessingController {
    private final String doctorApiUrl = System.getenv("DOCTOR_API_URL");
    @Autowired
    private DynamoDBService dynamoDBService;
    private RestTemplate restTemplate;
    @Autowired
    private S3Service s3Service;
    
    public Doctor getDoctor() {
        return restTemplate.getForObject(doctorApiUrl, Doctor.class);
    }
    
    public void processCaramel(@RequestBody Caramel caramel) {
        dynamoDBService.saveCaramel(caramel);
    }
    
    public void processPancake(Pancake pancake) throws TheOnlyException {
        dynamoDBService.savePancake(pancake);
        try {
            Doctor doctor = getDoctor();
            try {
                s3Service.saveDoctor(doctor);
            } catch (Exception e) {
                throw new TheOnlyException("Taylor Swift isn't so cool");
            }
        } catch (Exception e) {
            throw new TheOnlyException("you, are");
        }
    }
}
