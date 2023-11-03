package com.example.foodprocessor.model;

import java.util.List;

public record Doctor(String speciality, int age, int yearsOfExperience, String ethicalConduct, List<String> peerReviews) {
}
