package com.restassured.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Game {
    private String name;
    private double price;
    private int strorage;
}
