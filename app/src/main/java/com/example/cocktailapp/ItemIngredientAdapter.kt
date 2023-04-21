package com.example.cocktailapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemIngredientAdapter(var ingredientList: List<IngredientsListResponse> = emptyList()) :
    RecyclerView.Adapter<IngredientsItemsViewholder>() {
    fun updateIngredient(ingredientList: List<IngredientsListResponse>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsItemsViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IngredientsItemsViewholder(
            layoutInflater.inflate(
                R.layout.item_ingredient,
                parent,
                false
            )
        )

    }



    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientsItemsViewholder, position: Int) {
        holder.render(ingredientList[position])
    }

}
