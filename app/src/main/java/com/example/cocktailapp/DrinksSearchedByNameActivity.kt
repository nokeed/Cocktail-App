package com.example.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.databinding.ActivityDrinksSearchedByNameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DrinksSearchedByNameActivity : AppCompatActivity() {

    companion object {
        var DRINK_NAME = ""
        var CATEGORY_NAME = ""
        var INGREDIENT_NAME = ""
    }

    private lateinit var binding: ActivityDrinksSearchedByNameBinding
    private lateinit var adapter: DrinksItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityDrinksSearchedByNameBinding.inflate(layoutInflater)

        val drinkNameOrLetter: String = intent.getStringExtra(DRINK_NAME).orEmpty()
        val categoryName: String = intent.getStringExtra(CATEGORY_NAME).orEmpty()
        val ingredientName: String = intent.getStringExtra(INGREDIENT_NAME).orEmpty()
        if (drinkNameOrLetter.length == 1 && drinkNameOrLetter.isNotEmpty()) {
            initAdapter()
            searchDrinkByLetter(drinkNameOrLetter)
        }
        if (drinkNameOrLetter.length > 1 && drinkNameOrLetter.isNotEmpty()) {
            initAdapter()
            searchDrinkByName(drinkNameOrLetter)
        }
        if (categoryName.isNotEmpty()) {
            initAdapter()
            searchDrinkByCategory(categoryName)
        }
        if (ingredientName.isNotEmpty()) {
            initAdapter()
            searchDrinkByIngredient(ingredientName)
        }
//        hideSystemUI()
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        }
    }

    private fun searchDrinkByIngredient(ingredientName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse =
                getRetrofit().create(ApiService::class.java).getDrinksByIngredient(ingredientName)
            if (myResponse.isSuccessful) {
                val response: CocktailResponse? = myResponse.body()
                if (response != null) {
                    runOnUiThread {
                        adapter.updateList(response.drinks)
                        setContentView(binding.root)

                    }
                }
            }
        }
    }

    private fun searchDrinkByCategory(categoryName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse =
                getRetrofit().create(ApiService::class.java).getDrinksByCategory(categoryName)
            if (myResponse.isSuccessful) {
                val response: CocktailResponse? = myResponse.body()
                if (response != null) {
                    runOnUiThread {
                        adapter.updateList(response.drinks)
                        setContentView(binding.root)

                    }
                } else {
                    runOnUiThread {
                        adapter.updateList(emptyList())
                    }
                }
            }
        }
    }


    private fun searchDrinkByLetter(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = getRetrofit()
            val myResponse = retrofit.create(ApiService::class.java).getDrinksByLetter(query)
            if (myResponse.isSuccessful) {
                val response: CocktailResponse? = myResponse.body()
                runOnUiThread {
                    if (response?.drinks != null) {
                        adapter.updateList(response.drinks)
                        setContentView(binding.root)

                    } else {
                        adapter.updateList(emptyList())
                        Log.i("noked", "Lista vacia o null")
                    }
                }
            }
        }
    }

    private fun searchDrinkByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = getRetrofit()
            val myResponse = retrofit.create(ApiService::class.java).getDrinksByName(query)
            if (myResponse.isSuccessful) {
                val response: CocktailResponse? = myResponse.body()
                if (response?.drinks != null) {
                    runOnUiThread {
                        adapter.updateList(response.drinks)
                        setContentView(binding.root)

                    }
                    Log.i("noked", response.toString())
                } else {
                    runOnUiThread {
                        adapter.updateList(emptyList())
                        Log.i("noked", "Lista vacia o null")
                    }

                }
            }
        }
    }

    fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initAdapter() {
        adapter = DrinksItemsAdapter()
        binding.rvDrinksSrcName.setHasFixedSize(true)
        binding.rvDrinksSrcName.layoutManager = LinearLayoutManager(this)
        binding.rvDrinksSrcName.adapter = adapter

    }
}
