package org.tamersarioglu.easywidgets.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.data.WidgetCategory
import org.tamersarioglu.easywidgets.ui.components.WidgetCard

@Composable
fun HomeScreen(
    widgets: List<Widget>,
    onWidgetClick: (Widget) -> Unit,
    onFavoriteToggle: (Widget) -> Unit,
    onCategorySelected: (WidgetCategory?) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    onSearchQueryChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search widgets...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                singleLine = true
            )
            
            // Categories
            CategorySelector(
                selectedCategory = null,
                onCategorySelected = { category ->
                    onCategorySelected(category)
                }
            )
            
            // Widget List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(widgets) { widget ->
                    WidgetCard(
                        widget = widget,
                        onCardClick = { clickedWidget ->
                            println("HomeScreen: Widget clicked: ${clickedWidget.name}")
                            onWidgetClick(clickedWidget)
                        },
                        onFavoriteToggle = onFavoriteToggle
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySelector(
    selectedCategory: WidgetCategory?,
    onCategorySelected: (WidgetCategory?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        CategoryChip(
            title = "All",
            selected = selectedCategory == null,
            onClick = { onCategorySelected(null) }
        )
        
        WidgetCategory.entries.forEach { category ->
            CategoryChip(
                title = category.title,
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(title) },
        modifier = Modifier.padding(end = 8.dp)
    )
} 