package com.example.newsapp.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class Recipe(
    val id: Int? = null,
    val title: String? = null,
    val publisher: String? = null,
    val featuredImage: String? = null,
    val rating: Int? = 0,
    val sourceUrl: String? = null,
    val description: String? = null,
    val ingredients: List<String> = listOf(),
    val dateAdded: String? = null,
    val dateUpdated: String? = null,
    val cookingInstructions: String? = null,
) : Parcelable