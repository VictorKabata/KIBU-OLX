package com.ifixhubke.kibu_olx.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ifixhubke.kibu_olx.R
import com.ifixhubke.kibu_olx.databinding.ItemCategoryBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)

        return CategoriesViewHolder(binding)
    }

    override fun getItemCount() = categoriesList.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categoriesList[position]
        val context = holder.itemView.context

        holder.bind(context, category)
    }

    inner class CategoriesViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, category: Category) {
            Glide.with(context).load(category.imageUrl).into(binding.imageViewCategory)
            binding.textViewCategory.text = category.categoryName
        }

    }

    data class Category(val imageUrl: Int, val categoryName: String)

    private val categoriesList = listOf(
        Category(R.drawable.ic_bed, "Beds"),
        Category(R.drawable.ic_bed, "Phones"),
        Category(R.drawable.ic_bed, "Gas"),
        Category(R.drawable.ic_bed, "Shoes"),
        Category(R.drawable.ic_bed, "Clothes"),
    )

}