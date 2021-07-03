package com.ifixhubke.kibu_olx.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ifixhubke.kibu_olx.databinding.ItemProductBinding
import com.ifixhubke.kibu_olx.models.Products

class HomeProductsAdapter constructor(private val productsList: List<Products>) :
    RecyclerView.Adapter<HomeProductsAdapter.HomeProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)

        return HomeProductsViewHolder(binding)
    }

    override fun getItemCount() = productsList.size

    override fun onBindViewHolder(holder: HomeProductsViewHolder, position: Int) {
        val product = productsList[position]
        val context = holder.itemView.context

        holder.bind(context, product)
    }

    inner class HomeProductsViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val imageViewFavourite = binding.imageViewFavorite

        fun bind(context: Context, product: Products) {
            Glide.with(context).load(product.itemImage).into(binding.imageViewProduct)
            binding.textViewName.text = product.itemName
            binding.textViewPrice.text = product.itemPrice
        }

    }

}