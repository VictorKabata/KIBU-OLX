package com.ifixhubke.kibu_olx.repositories

import com.ifixhubke.kibu_olx.models.Products
import com.ifixhubke.kibu_olx.network.FirebaseSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    suspend fun fetchCurrentUser()=firebaseSource.fetchCurrentUser()

    suspend fun fetchItems(): Flow<List<Products>> {
        return firebaseSource.fetchProducts()
    }
}