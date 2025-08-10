package com.example.newsapp.di

import androidx.compose.foundation.layout.Row
import com.example.newsapp.network.RecipeService
import com.example.newsapp.network.model.RecipeDtoMapper
import com.example.newsapp.repository.RecipeRepository
import com.example.newsapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }
}