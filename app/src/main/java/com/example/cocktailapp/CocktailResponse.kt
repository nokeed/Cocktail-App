package com.example.cocktailapp

import com.google.gson.annotations.SerializedName

data class CocktailResponse(@SerializedName("drinks") val drinks: List<DrinksResponseList>)
// para buscar por nombre y primera letra
data class DrinksResponseList(
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val drinkCategory:String,
    @SerializedName("strDrinkThumb") val drinkImage:String,
    @SerializedName("idDrink") val id:String
)
