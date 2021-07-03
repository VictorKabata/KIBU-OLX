package com.ifixhubke.kibu_olx.ui.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ifixhubke.kibu_olx.R
import com.ifixhubke.kibu_olx.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)


        binding.bottomNav.background = null
        binding.bottomNav.menu.getItem(1).isEnabled = false

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment -> {
                    binding.bottomBar.visibility = VISIBLE
                    binding.fabAddTask.visibility = VISIBLE
                }
                R.id.favorites_fragment -> {
                    binding.bottomBar.visibility = VISIBLE
                    binding.fabAddTask.visibility = VISIBLE
                }
                else -> {
                    binding.bottomBar.visibility = GONE
                    binding.fabAddTask.visibility = GONE
                }
            }
        }

        binding.fabAddTask.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.home_to_sell_one)
        }

    }
}