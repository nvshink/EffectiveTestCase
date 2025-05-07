package com.nvshink.effectivetestcase.di

import android.content.Context
import com.nvshink.effectivetestcase.data.datastore.AppPreferences
import com.nvshink.effectivetestcase.data.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//TODO: Add multi modules architecture
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context.dataStore)
    }
}