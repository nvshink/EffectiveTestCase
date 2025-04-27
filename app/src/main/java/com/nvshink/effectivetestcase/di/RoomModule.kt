package com.nvshink.effectivetestcase.di

import android.content.Context
import androidx.room.Room
import com.nvshink.effectivetestcase.data.dao.FavoriteCourseDao
import com.nvshink.effectivetestcase.data.repository.CourseRepositoryImpl
import com.nvshink.effectivetestcase.data.room.Database
import com.nvshink.effectivetestcase.domain.repository.CourseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "local_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCourseDao(db: Database): FavoriteCourseDao = db.favoriteCourseDao


}