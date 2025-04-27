package com.nvshink.effectivetestcase.di

import com.nvshink.effectivetestcase.data.repository.CourseRepositoryImpl
import com.nvshink.effectivetestcase.data.repository.FavoriteCourseRepositoryImpl
import com.nvshink.effectivetestcase.data.repository.OnboardingItemRepositoryImpl
import com.nvshink.effectivetestcase.data.repository.ProfileRepositoryImpl
import com.nvshink.effectivetestcase.domain.repository.CourseRepository
import com.nvshink.effectivetestcase.domain.repository.FavoriteCourseRepository
import com.nvshink.effectivetestcase.domain.repository.OnboardingItemRepository
import com.nvshink.effectivetestcase.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindsCourseRepository(courseRepositoryImpl: CourseRepositoryImpl): CourseRepository
    @Binds
    @Singleton
    abstract fun bindsFavoriteCourseRepository(favoriteCourseRepositoryImpl: FavoriteCourseRepositoryImpl): FavoriteCourseRepository
    @Binds
    @Singleton
    abstract fun bindsOnboardingItemRepository(onboardingItemRepositoryImpl: OnboardingItemRepositoryImpl): OnboardingItemRepository
    @Binds
    @Singleton
    abstract fun bindsProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}