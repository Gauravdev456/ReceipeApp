package com.example.newsapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Recipe
import com.example.newsapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import androidx.compose.runtime.State
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {
    val _recipes: MutableState<List<Recipe>> = mutableStateOf(emptyList())
    val query = mutableStateOf("Chicken")
    private val _scrollPosition = mutableStateOf(0)
    val scrollPosition: State<Int> = _scrollPosition

    private val _scrollOffset = mutableStateOf(0)
    val scrollOffset: State<Int> = _scrollOffset

    fun updateScrollPosition(position: Int, offset: Int) {
        _scrollPosition.value = position
        _scrollOffset.value = offset
    }
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    init {
        performSearch()
    }
    fun performSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.search(
                    token = token,
                    page = 1,
                    query = query.value
                )
                launch(Dispatchers.Main) {
                    _recipes.value = result
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    _recipes.value = emptyList()
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }
}
