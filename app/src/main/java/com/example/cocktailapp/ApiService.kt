package com.example.cocktailapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //get drink by name
    @GET("search.php")
    suspend fun getDrinksByName(@Query("s") drink: String): Response<CocktailResponse>

    @GET("search.php")
    suspend fun getDrinksByLetter(@Query("f") drinkletter: String): Response<CocktailResponse>

    @GET("filter.php")
//    suspend fun getDrinksByIngredient(@Query("i") drinkIngredient: String): Response<CocktailByIngredientResponse>
    suspend fun getDrinksByIngredient(@Query("i") drinkIngredient: String): Response<CocktailResponse>

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") drinkCategory: String): Response<CocktailResponse>

    @GET("lookup.php")
    suspend fun getDrinkDetailsById(@Query("i") drinkId: String): Response<CocktailDetailResponse>

    //obtener ingredientes
    @GET("list.php")
    suspend fun getIngredients(@Query("i") typelist: String): Response<IngredientsResponse>

    //obtener categorias
    @GET("list.php")
    suspend fun getCategories(@Query("c") typelist: String): Response<CategoriesResponse>

    //falta random
    @GET("random.php")
    suspend fun getRandomDrink():Response<CocktailDetailResponse>
}
