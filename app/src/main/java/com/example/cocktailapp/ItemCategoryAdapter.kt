package com.example.cocktailapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemCategoryAdapter(var categoryList: List<CategoriesListResponse> = emptyList()) :
    RecyclerView.Adapter<ItemCategoryViewHolder>() {

    fun updateCategories(categoryList: List<CategoriesListResponse>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemCategoryViewHolder(layoutInflater.inflate(R.layout.item_search_categoty,parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        holder.render(categoryList[position])
    }
}