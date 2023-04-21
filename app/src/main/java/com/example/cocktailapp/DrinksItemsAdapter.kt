package com.example.cocktailapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DrinksItemsAdapter(var drinksList: List<DrinksResponseList> = emptyList()) :
    RecyclerView.Adapter<DrinksViewHolder>() {

    fun updateList(drinklist: List<DrinksResponseList>) {
        this.drinksList = drinklist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DrinksViewHolder(layoutInflater.inflate(R.layout.item_drink, parent, false))
    }

    override fun getItemCount(): Int {
        return drinksList.size

    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        holder.render(drinksList[position])
    }

}
