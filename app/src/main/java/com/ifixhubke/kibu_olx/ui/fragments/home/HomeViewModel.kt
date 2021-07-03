package com.ifixhubke.kibu_olx.ui.fragments.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.ifixhubke.kibu_olx.models.Products
import com.ifixhubke.kibu_olx.repositories.HomeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> get() = _currentUser

    private val _allProducts = MutableLiveData<List<Products>>()
    val allProducts: LiveData<List<Products>> get() = _allProducts

    init {
        fetCurrentUser()

        fetchAllProducts()
    }

    private fun fetCurrentUser() {
        viewModelScope.launch {
            val response = homeRepository.fetchCurrentUser()
            _currentUser.value = response
        }
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            val response = homeRepository.fetchItems()
            response.collect { products->
                _allProducts.value=products
            }
        }
    }

}