package com.ifixhubke.kibu_olx.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ifixhubke.kibu_olx.models.Products
import com.ifixhubke.kibu_olx.utils.Constants.ALL_ITEMS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

@ExperimentalCoroutinesApi
class FirebaseSource {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()


    suspend fun fetchCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun fetchProducts(): Flow<List<Products>> = callbackFlow {
        val databaseReference = firebaseDatabase.getReference(ALL_ITEMS)
        val productsList = mutableListOf<Products>()

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val response = it.getValue(Products::class.java)
                    if (response != null) productsList.add(response)
                }
                offer(productsList as List<Products>)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Cancelled")
            }
        }

        databaseReference.addValueEventListener(valueEventListener)

        //If collect is not in use or collecting any data we cancel this channel to prevent any leaks
        awaitClose { databaseReference.removeEventListener(valueEventListener) }
    }
}