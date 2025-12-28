package com.example.perfume_shop.models;

import com.google.gson.annotations.SerializedName;

public class Perfume {
    @SerializedName("Name")
    private String name;
    
    @SerializedName("Brand")
    private String brand;
    
    @SerializedName("Year")
    private String year;
    
    @SerializedName("rating")
    private String rating;
    
    @SerializedName("Country")
    private String country;
    
    @SerializedName("Price")
    private String price;
    
    @SerializedName("Image URL")
    private String imageUrl;
    
    @SerializedName("Gender")
    private String gender;
    
    @SerializedName("Longevity")
    private String longevity;
    
    @SerializedName("Sillage")
    private String sillage;
    
    @SerializedName("OilType")
    private String oilType;
    
    // For local use (not from API)
    private int id;
    private String description;

    public Perfume() {
    }

    public Perfume(String name, String brand, String price, String description, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    
    public Perfume(String name, String brand, double price, String description, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.price = String.valueOf(price);
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand != null ? brand : "";
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        if (price == null || price.isEmpty()) return 0.0;
        try {
            // Remove $ and any other non-numeric characters except decimal point
            String cleanPrice = price.replaceAll("[^0-9.]", "");
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setPrice(double price) {
        this.price = String.valueOf(price);
    }
    
    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        // Build description from available data
        if (description != null && !description.isEmpty()) {
            return description;
        }
        
        StringBuilder desc = new StringBuilder();
        if (oilType != null && !oilType.isEmpty()) {
            desc.append(oilType).append(" • ");
        }
        if (gender != null && !gender.isEmpty()) {
            desc.append("For ").append(gender).append(" • ");
        }
        if (longevity != null && !longevity.isEmpty()) {
            desc.append(longevity).append(" lasting");
        }
        
        return desc.length() > 0 ? desc.toString() : "Premium Fragrance";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Convert to transparent background if needed
            return imageUrl.replace(".jpg", ".webp");
        }
        return "";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getYear() {
        return year != null ? year : "";
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getRating() {
        return rating != null ? rating : "";
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    public String getCountry() {
        return country != null ? country : "";
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getGender() {
        return gender != null ? gender : "";
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getLongevity() {
        return longevity != null ? longevity : "";
    }
    
    public void setLongevity(String longevity) {
        this.longevity = longevity;
    }
    
    public String getSillage() {
        return sillage != null ? sillage : "";
    }
    
    public void setSillage(String sillage) {
        this.sillage = sillage;
    }
    
    public String getOilType() {
        return oilType != null ? oilType : "";
    }
    
    public void setOilType(String oilType) {
        this.oilType = oilType;
    }
}
