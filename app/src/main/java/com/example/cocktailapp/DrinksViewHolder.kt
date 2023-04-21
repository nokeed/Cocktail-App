package com.example.cocktailapp

import android.content.Intent
import android.view.RoundedCorner
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktailapp.CocktailDetailActivity.Companion.DRINK_ID
import com.example.cocktailapp.databinding.ItemDrinkBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class DrinksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var binding = ItemDrinkBinding.bind(view)


    fun render(drinksResults: DrinksResponseList) {

        binding.ivDrinkImg.load(drinksResults.drinkImage){
            transformations(coil.transform.RoundedCornersTransformation(100f))}
        binding.tvDrinkName.text = drinksResults.name
        binding.tvDrinkCategory.text = drinksResults.drinkCategory
        binding.cvItemDrink.setOnClickListener {
            DRINK_ID = drinksResults.id
            navigateToDrinkDetail(DRINK_ID)
        }

    }

    private fun navigateToDrinkDetail(drinkId: String) {
        val intent= Intent(binding.cvItemDrink.context,CocktailDetailActivity::class.java)
        intent.putExtra(drinkId,drinkId)
        binding.cvItemDrink.context.startActivity(intent)
    }

}
