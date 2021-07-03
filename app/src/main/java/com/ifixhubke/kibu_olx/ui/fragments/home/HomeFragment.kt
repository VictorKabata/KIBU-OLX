package com.ifixhubke.kibu_olx.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ifixhubke.kibu_olx.R
import com.ifixhubke.kibu_olx.databinding.FragmentHomeBinding
import com.ifixhubke.kibu_olx.models.Products
import com.ifixhubke.kibu_olx.ui.adapters.CategoriesAdapter
import com.ifixhubke.kibu_olx.ui.adapters.HomeProductsAdapter
import com.ifixhubke.kibu_olx.ui.adapters.HomeViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        initUI()

    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            Timber.e("Current user: ${user?.displayName}")
            binding.textViewGreeting.text = "Hello, ${user!!.email}"
        }

        viewModel.allProducts.observe(viewLifecycleOwner) { initTrendingViewPager(it) }

        viewModel.allProducts.observe(viewLifecycleOwner) { products ->
            binding.recyclerviewProducts.adapter = HomeProductsAdapter(products)
        }

        binding.recyclerviewCategories.adapter = CategoriesAdapter()


    }

    private fun initTrendingViewPager(products: List<Products>) {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(16))
        compositePageTransformer.addTransformer { page, position ->
            val transform = 1 - abs(position)
            page.scaleY = (0.85f + transform * 0.15f)
        }

        binding.viewPagerTrending.apply {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
            adapter = HomeViewPagerAdapter(requireActivity(), products)
        }

        //binding.dotsIndicator.setViewPager(binding.viewPagerTrending)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}