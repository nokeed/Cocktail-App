package com.example.cocktailapp

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.DrinksSearchedByNameActivity.Companion.INGREDIENT_NAME
import com.example.cocktailapp.databinding.ItemIngredientBinding

class IngredientsItemsViewholder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemIngredientBinding.bind(view)
    fun render(ingredientsResult: IngredientsListResponse) {

        binding.tvIngredientItem.text = ingredientsResult.ingredient

        binding.cvitemIngredient.setOnClickListener {
            INGREDIENT_NAME = binding.tvIngredientItem.text.toString()
            navigatetoSearchedByIngredient(INGREDIENT_NAME)
        }


    }

    private fun navigatetoSearchedByIngredient(ingredientName:String) {
        val intent = Intent(binding.tvIngredientItem.context,DrinksSearchedByNameActivity::class.java)
        intent.putExtra(ingredientName,ingredientName)
        binding.tvIngredientItem.context.startActivity(intent)
    }

}
