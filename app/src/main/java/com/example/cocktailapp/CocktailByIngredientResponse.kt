package com.example.cocktailapp

import com.google.gson.annotations.SerializedName

data class CocktailByIngredientResponse(@SerializedName("drinks") val drinks: List<DrinksByIngredientList>)

data class DrinksByIngredientList(
    @SerializedName("strDrink") val drinkName: String,
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrinkThumb") val drinkImage: String
)
