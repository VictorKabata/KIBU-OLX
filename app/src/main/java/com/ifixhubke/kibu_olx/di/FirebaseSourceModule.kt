package com.ifixhubke.kibu_olx.di

import com.ifixhubke.kibu_olx.network.FirebaseSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)

@Module
class FirebaseSourceModule {

    @Singleton
    @Provides
    fun providesFirebaseDatasource(): FirebaseSource {
        return FirebaseSource()
    }

}