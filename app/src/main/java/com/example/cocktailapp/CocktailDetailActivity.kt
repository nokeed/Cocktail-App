package com.example.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import coil.load
import com.example.cocktailapp.databinding.ActivityCocktailDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CocktailDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCocktailDetailBinding
    private lateinit var ingreAdapter: ArrayAdapter<*>
    private lateinit var measureAdapter: ArrayAdapter<*>

    companion object {
        var DRINK_ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityCocktailDetailBinding.inflate(layoutInflater)
//        hideSystemUI()
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse =
                getRetrofit().create(ApiService::class.java).getDrinkDetailsById(DRINK_ID)
            if (myResponse.isSuccessful) {
                val category = myResponse.body()?.drinks?.get(0)?.category
                val glass = myResponse.body()?.drinks?.get(0)?.glass
                val name = myResponse.body()?.drinks?.get(0)?.name
                val imagen = myResponse.body()?.drinks?.get(0)?.image
                val instructions = myResponse.body()?.drinks?.get(0)?.instructions
                val medidas = arrayOf(
                    myResponse.body()?.drinks?.get(0)?.measure1,
                    myResponse.body()?.drinks?.get(0)?.measure2,
                    myResponse.body()?.drinks?.get(0)?.measure3,
                    myResponse.body()?.drinks?.get(0)?.measure4,
                    myResponse.body()?.drinks?.get(0)?.measure5,
                    myResponse.body()?.drinks?.get(0)?.measure6,
                    myResponse.body()?.drinks?.get(0)?.measure7,
                    myResponse.body()?.drinks?.get(0)?.measure8,
                    myResponse.body()?.drinks?.get(0)?.measure9,
                    myResponse.body()?.drinks?.get(0)?.measure10,
                    myResponse.body()?.drinks?.get(0)?.measure11,
                    myResponse.body()?.drinks?.get(0)?.measure12,
                    myResponse.body()?.drinks?.get(0)?.measure13,
                    myResponse.body()?.drinks?.get(0)?.measure14,
                    myResponse.body()?.drinks?.get(0)?.measure15,
                )
                val newmedidas = medidas.filterNotNull()
                val ingredients = arrayOf(
                    myResponse.body()?.drinks?.get(0)?.ingredient1,
                    myResponse.body()?.drinks?.get(0)?.ingredient2,
                    myResponse.body()?.drinks?.get(0)?.ingredient3,
                    myResponse.body()?.drinks?.get(0)?.ingredient4,
                    myResponse.body()?.drinks?.get(0)?.ingredient5,
                    myResponse.body()?.drinks?.get(0)?.ingredient6,
                    myResponse.body()?.drinks?.get(0)?.ingredient7,
                    myResponse.body()?.drinks?.get(0)?.ingredient8,
                    myResponse.body()?.drinks?.get(0)?.ingredient9,
                    myResponse.body()?.drinks?.get(0)?.ingredient10,
                    myResponse.body()?.drinks?.get(0)?.ingredient11,
                    myResponse.body()?.drinks?.get(0)?.ingredient12,
                    myResponse.body()?.drinks?.get(0)?.ingredient13,
                    myResponse.body()?.drinks?.get(0)?.ingredient14,
                    myResponse.body()?.drinks?.get(0)?.ingredient15,
                )
                val newingredients = ingredients.filterNotNull()

                runOnUiThread {
                    ingreAdapter = ArrayAdapter(
                        binding.lvIngredients.context,
                        R.layout.item_ingredient_measure,
                        R.id.tvIngredientmeasure,
                        newingredients
                    )
                    measureAdapter = ArrayAdapter(
                        binding.lvIngredients.context,
                        R.layout.item_ingredient_measure,
                        R.id.tvIngredientmeasure,
                        newmedidas
                    )
                    binding.ivbackground.load(imagen)
                    binding.tvDrinkName.text = name
                    binding.tvInstructions.text = instructions
                    binding.tvCategory.text = category
                    binding.tvGlass.text = glass
                    binding.lvIngredients.adapter = ingreAdapter
                    binding.lvMedidas.adapter = measureAdapter
                    binding.tvInstructions.maxHeight = 200
                    binding.cvInstruction.setOnClickListener {
                        if (binding.tvInstructions.maxHeight == 200) {
                            binding.tvInstructions.maxHeight = 800
                        } else {
                            binding.tvInstructions.maxHeight = 200
                        }
                    }
                    setContentView(binding.root)


                }
            } else {
                binding.tvDrinkName.text = "Error"
            }
        }


    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}