package com.example.foodprocessor.model;

import java.util.List;


public record Caramel(
    String color,
    String flavor,
    String texture,
    int sweetness,
    List<String> famousPeopleReviews
) {
}