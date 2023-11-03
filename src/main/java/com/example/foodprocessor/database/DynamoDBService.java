package com.example.foodprocessor.database;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.foodprocessor.config.DynamoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.foodprocessor.model.Caramel;
import com.example.foodprocessor.model.Pancake;

import java.util.UUID;

@Service
public class DynamoDBService {
    
    @Autowired
    private DynamoConfig amazonDynamoDB;
    
    private DynamoDB dynamoDB;
    
    @PostConstruct
    public void init() {
        this.dynamoDB = new DynamoDB((AmazonDynamoDB) amazonDynamoDB);
    }
    
    public void saveCaramel(Caramel caramel) {
        Table table = dynamoDB.getTable("Caramels");
        
        Item item = new Item()
            .withPrimaryKey("CaramelId", UUID.randomUUID())
            .withString("Color", caramel.color())
            .withString("Flavor", caramel.flavor())
            .withString("Texture", caramel.texture())
            .withInt("Sweetness", caramel.sweetness())
            .withList("FamousPeopleReviews", caramel.famousPeopleReviews());
        
        table.putItem(item);
    }
    
    public void savePancake(Pancake pancake) {
        Table table = dynamoDB.getTable("Pancakes");
        
        Item item = new Item()
            .withPrimaryKey("PancakeId", UUID.randomUUID())
            .withBoolean("IsUnpleasant", pancake.isUnpleasant());
        
        table.putItem(item);
    }
}
