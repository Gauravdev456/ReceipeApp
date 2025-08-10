package com.example.newsapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.newsapp.R
import com.example.newsapp.presentation.components.RecipeCard

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel._recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val scrollPosition = viewModel.scrollPosition.value
                val scrollOffset = viewModel.scrollOffset.value
                val categories = getAllFoodCategories()
                val selectedIndex = categories.indexOfFirst { it == selectedCategory }
                val listState = rememberLazyListState(
                    initialFirstVisibleItemIndex = scrollPosition,
                    initialFirstVisibleItemScrollOffset = scrollOffset
                )
                Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ){
                            OutlinedTextField(
                                value = query,
                                onValueChange = { viewModel.onQueryChanged(it) },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Search") },
                                shape = RoundedCornerShape(40.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        viewModel.performSearch() // Replace with your actual search call
                                    }
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search Icon"
                                    )
                                }
                            )
                        }
                    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
                        viewModel.updateScrollPosition(
                            listState.firstVisibleItemIndex,
                            listState.firstVisibleItemScrollOffset
                        )
                    }
                    LaunchedEffect(selectedCategory) {
                        if (selectedIndex != -1) {
                            val layoutInfo = listState.layoutInfo
                            val visibleItemsInfo = layoutInfo.visibleItemsInfo
                            val isSelectedVisible = visibleItemsInfo.any { it.index == selectedIndex }

                            if (!isSelectedVisible) {
                                listState.animateScrollToItem(selectedIndex)
                            }
                        }
                    }
                    LazyRow(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        itemsIndexed(categories) { index, category ->
                            FilterChip(
                                onClick = {
                                    viewModel.onSelectedCategoryChanged(category.value)
                                    viewModel.performSearch()
                                },
                                label = { Text(category.value) },
                                selected = selectedCategory == category,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                    LazyColumn {
                        itemsIndexed(items = recipes) { index, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }

        }
    }

}
