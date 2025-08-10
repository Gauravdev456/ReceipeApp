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

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {
    val _recipes: MutableState<List<Recipe>> = mutableStateOf(emptyList())
    val query = mutableStateOf("Chicken")

    init {
        performSearch()
    }
    fun performSearch(){
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            _recipes.value = result
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }
}
