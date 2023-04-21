package com.example.cocktailapp

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(@SerializedName("drinks") val drinks: List<IngredientsListResponse>)

data class IngredientsListResponse(@SerializedName("strIngredient1") val ingredient: String)
