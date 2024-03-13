package com.example.lab1products;

public class Product {
    private int product_id;
    private String product_name;
    private int calories_per_serving;
    private NutritionalValue nutritional_value;
    private String ingredients;
    private String harmful_information;
    private String contraindications;
    private String category;
    private boolean favorite;


    public Product(int product_id, String product_name, int calories_per_serving, NutritionalValue nutritional_value, String ingredients, String harmful_information, String contraindications, String category, boolean favorite) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.calories_per_serving = calories_per_serving;
        this.nutritional_value = nutritional_value;
        this.ingredients = ingredients;
        this.harmful_information = harmful_information;
        this.contraindications = contraindications;
        this.category = category;
        this.favorite = favorite;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getCalories_per_serving() {
        return calories_per_serving;
    }

    public void setCalories_per_serving(int calories_per_serving) {
        this.calories_per_serving = calories_per_serving;
    }

    public NutritionalValue getNutritional_value() {
        return nutritional_value;
    }

    public void setNutritional_value(NutritionalValue nutritional_value) {
        this.nutritional_value = nutritional_value;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getHarmful_information() {
        return harmful_information;
    }

    public void setHarmful_information(String harmful_information) {
        this.harmful_information = harmful_information;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
