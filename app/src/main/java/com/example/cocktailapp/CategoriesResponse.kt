package com.example.cocktailapp

import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class CategoriesResponse (@SerializedName("drinks") val drinks:List<CategoriesListResponse>)

data class CategoriesListResponse (@SerializedName("strCategory") val categories:String)
