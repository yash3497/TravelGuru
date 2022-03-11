package com.example.travelguru.Model;

import java.util.ArrayList;

public class City {
    String name,image,description,map,url;


    public City(String name, String image, String description, String map, String url) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.map = map;
        this.url = url;
    }

    public City() {
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
