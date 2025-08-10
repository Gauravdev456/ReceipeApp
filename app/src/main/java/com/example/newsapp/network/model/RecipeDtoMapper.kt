package com.example.newsapp.network.model

import com.example.newsapp.domain.model.Recipe
import com.example.newsapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            featuredImage = model.featuredImage,
            rating = model.rating,
            dateAdded = model.dateAdded,
            publisher = model.publisher,
            sourceUrl = model.sourceUrl,
            dateUpdated = model.dateUpdated,
            description = model.description,
            ingredients = model.ingredients ?: listOf(),
            cookingInstructions = model.cookingInstructions
        )
    }

    override fun mapFromDomainModel(model: Recipe): RecipeDto {
        return RecipeDto(
            pk = model.id,
            title = model.title,
            featuredImage = model.featuredImage,
            rating = model.rating,
            dateAdded = model.dateAdded,
            publisher = model.publisher,
            sourceUrl = model.sourceUrl,
            dateUpdated = model.dateUpdated,
            description = model.description,
            ingredients = model.ingredients,
            cookingInstructions = model.cookingInstructions
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}