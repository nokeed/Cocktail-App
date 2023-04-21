package com.example.cocktailapp


import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.CocktailDetailActivity.Companion.DRINK_ID
import com.example.cocktailapp.DrinksSearchedByNameActivity.Companion.DRINK_NAME
import com.example.cocktailapp.databinding.ActivityMainBinding
import com.example.cocktailapp.databinding.DialogFirstLetterBinding
import com.example.cocktailapp.databinding.DialogNameBinding
import com.example.cocktailapp.databinding.DialogSearchCategoryBinding
import com.example.cocktailapp.databinding.DialogSearchIngredientBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: DialogNameBinding
    private lateinit var bindingDialogLetter: DialogFirstLetterBinding
    private lateinit var bindingDialogCategory: DialogSearchCategoryBinding
    private lateinit var adapter: ItemCategoryAdapter
    private lateinit var bindingDialogIngredient: DialogSearchIngredientBinding
    private lateinit var adapterIngredient: ItemIngredientAdapter

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        window.insetsController?.hide(WindowInsets.Type.statusBars())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
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

    private fun initListeners() {
        binding.cvSearchByName.setOnClickListener {
            showSearchByNameDialog()

        }
        binding.cvSearchByFirstLetter.setOnClickListener {
            showSearchByFirstLetterDialog()
        }
        binding.cvSearchByCategory.setOnClickListener {
            showSearchCategoryDialog()
        }
        binding.cvSearchByIngredient.setOnClickListener {
            showSearchIngredientDialog()
        }
        binding.btnRandom.setOnClickListener {
            getRandomDrink()
        }
    }

    private fun getRandomDrink() {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = getRetrofit().create(ApiService::class.java).getRandomDrink()
            if (myResponse.isSuccessful) {
                val idRandomDrink = myResponse.body()?.drinks?.get(0)?.id
                if (idRandomDrink != null) {
                    runOnUiThread {
                        DRINK_ID = idRandomDrink
                        println(DRINK_ID)
                        navigateToDrinksDetail(DRINK_ID)
                    }

                } else {
                    Log.i("noked", "la respuesta es null")
                }

            } else {
                Log.i("noked", "no es suscesfull")
            }
        }


    }

    private fun showSearchIngredientDialog() {
        val dialog = Dialog(this)
        val li = LayoutInflater.from(dialog.context)
        bindingDialogIngredient = DialogSearchIngredientBinding.inflate(li)
        dialog.setContentView(bindingDialogIngredient.root)
        //init adapter
        adapterIngredient = ItemIngredientAdapter()
        bindingDialogIngredient.rvIngredientSearch.setHasFixedSize(true)
        bindingDialogIngredient.rvIngredientSearch.layoutManager = LinearLayoutManager(this)
        bindingDialogIngredient.rvIngredientSearch.adapter = adapterIngredient
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = getRetrofit().create(ApiService::class.java).getIngredients("list")
            if (myResponse.isSuccessful) {
                val response: IngredientsResponse? = myResponse.body()
                if (response?.drinks != null) {
                    runOnUiThread {
                        adapterIngredient.updateIngredient(response.drinks)
                        dialog.show()
                        bindingDialogIngredient.svIngredientSearch.clearFocus()

                        bindingDialogIngredient.svIngredientSearch.setOnQueryTextListener(/* listener = */
                            object :
                                SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(query: String?): Boolean {
                                    return false
                                }

                                override fun onQueryTextChange(newText: String?): Boolean {
                                    val filterLister: List<IngredientsListResponse> =
                                        response.drinks.filter {
                                            it.ingredient.lowercase().contains(
                                                newText?.lowercase().orEmpty()
                                            )
                                        }
                                    adapterIngredient.updateIngredient(filterLister)
                                    return true
                                }

                            })
                    }

                }
            } else {
                dialog.hide()
            }
        }
    }

    private fun navigateToDrinksDetail(drinkid: String) {
        val intent = Intent(this, CocktailDetailActivity::class.java)
        intent.putExtra(drinkid, drinkid)
        startActivity(intent)
    }

    private fun navigateToDrinksList(drinkName: String) {
        val intent = Intent(this, DrinksSearchedByNameActivity::class.java)
        intent.putExtra(drinkName, drinkName)
        startActivity(intent)
    }

    private fun showSearchByNameDialog() {
        val dialog = Dialog(/* context = */ this)
        val li = LayoutInflater.from(dialog.context)
        bindingDialog = DialogNameBinding.inflate(/* inflater = */ li)
        dialog.setContentView(bindingDialog.root)
        dialog.show()
        //text listener
        bindingDialog.etnameSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bindingDialog.btnNameSearch.isEnabled =
                    bindingDialog.etnameSearch.text.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        bindingDialog.btnNameSearch.setOnClickListener {
            if (bindingDialog.etnameSearch.text.isNotEmpty()) {
                DRINK_NAME = bindingDialog.etnameSearch.text.toString()
                dialog.hide()
                navigateToDrinksList(DRINK_NAME)
            }
        }
    }

    private fun showSearchByFirstLetterDialog() {
        val dialog = Dialog(this)
        val li = LayoutInflater.from(dialog.context)
        bindingDialogLetter = DialogFirstLetterBinding.inflate(li)
        dialog.setContentView(bindingDialogLetter.root)
        dialog.show()
        //text listener
        bindingDialogLetter.etnameSearch.addTextChangedListener(/* watcher = */ object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bindingDialogLetter.btnNameSearch.isEnabled =
                    bindingDialogLetter.etnameSearch.text.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        bindingDialogLetter.btnNameSearch.setOnClickListener {
            DRINK_NAME = bindingDialogLetter.etnameSearch.text.toString()
            dialog.hide()
            navigateToDrinksList(DRINK_NAME)

        }
    }

    private fun showSearchCategoryDialog() {
        val dialog = Dialog(this)
        val li = LayoutInflater.from(dialog.context)
        bindingDialogCategory = DialogSearchCategoryBinding.inflate(li)
        dialog.setContentView(bindingDialogCategory.root)
        //init adapter
        adapter = ItemCategoryAdapter()
        bindingDialogCategory.rvSearchCategories.setHasFixedSize(true)
        bindingDialogCategory.rvSearchCategories.layoutManager = LinearLayoutManager(this)
        bindingDialogCategory.rvSearchCategories.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = getRetrofit().create(ApiService::class.java).getCategories("list")
            if (myResponse.isSuccessful) {

                val response: CategoriesResponse? = myResponse.body()
                if (response?.drinks != null) {
                    runOnUiThread {
                        adapter.updateCategories(response.drinks)
                        dialog.show()
                    }
                } else {
                    dialog.hide()
                }
            } else {
                dialog.hide()
            }
        }

    }

    public fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}









