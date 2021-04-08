package com.example.bmicalc.DTO;

public class RecipeDTO {

    private final String name;
    private final int kcal;
    private final String recipe;
    private final String image;

    public RecipeDTO(String name, int kcal, String recipe, String image) {
        this.name = name;
        this.kcal = kcal;
        this.recipe = recipe;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getKcal() {
        return kcal;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getImage() {
        return image;
    }
}
