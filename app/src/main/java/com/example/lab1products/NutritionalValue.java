package com.example.lab1products;

public class NutritionalValue {
    private double fat;
    private double protein;
    private double carbohydrates;

    public NutritionalValue(double fat, double protein, double carbohydrates) {
        this.fat = fat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}