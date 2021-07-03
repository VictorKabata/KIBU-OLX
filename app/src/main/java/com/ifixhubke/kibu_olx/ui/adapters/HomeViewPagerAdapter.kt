package com.ifixhubke.kibu_olx.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ifixhubke.kibu_olx.databinding.ItemTrendingProductBinding
import com.ifixhubke.kibu_olx.models.Products

class HomeViewPagerAdapter constructor(
    private val context: Context,
    private val productsList: List<Products>
) :  RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>()  {

    override fun getItemCount() = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewPagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrendingProductBinding.inflate(layoutInflater, parent, false)
        return HomeViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewPagerViewHolder, position: Int) {
        val product=productsList.reversed()[position]
        val context=holder.itemView.context

        holder.bind(context, product)
    }


    inner class HomeViewPagerViewHolder(private val binding: ItemTrendingProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, product: Products){
            Glide.with(context).load(product.itemImage).into(binding.imageViewProduct)

            binding.textViewProductName.text = product.itemName
            binding.textViewPrice.text = product.itemPrice
        }
    }


}