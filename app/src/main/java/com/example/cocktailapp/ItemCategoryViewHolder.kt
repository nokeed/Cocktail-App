package com.example.cocktailapp

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.databinding.ItemSearchCategotyBinding
import com.example.cocktailapp.DrinksSearchedByNameActivity.Companion.CATEGORY_NAME


class ItemCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding = ItemSearchCategotyBinding.bind(view)

    fun render(categoriesResult: CategoriesListResponse) {
        binding.btnCategoty.text = categoriesResult.categories
        binding.btnCategoty.setOnClickListener {
            CATEGORY_NAME = binding.btnCategoty.text.toString()
            navigateToDrinkList(CATEGORY_NAME)


        }
    }

    private fun navigateToDrinkList(categoryName: String) {
        val intent = Intent(binding.btnCategoty.context, DrinksSearchedByNameActivity::class.java)
        intent.putExtra(categoryName, categoryName)
        binding.btnCategoty.context.startActivity(intent)


    }
}




