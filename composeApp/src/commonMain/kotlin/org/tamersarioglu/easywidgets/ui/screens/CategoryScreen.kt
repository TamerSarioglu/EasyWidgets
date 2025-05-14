package org.tamersarioglu.easywidgets.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.data.WidgetCategory
import org.tamersarioglu.easywidgets.ui.components.WidgetCard

@Composable
fun CategoryScreen(
    category: WidgetCategory,
    widgets: List<Widget>,
    onWidgetClick: (Widget) -> Unit,
    onFavoriteToggle: (Widget) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Category Title
            Text(
                text = category.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    onSearchQueryChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search widgets...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                singleLine = true
            )
            
            // Widget List
            if (widgets.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No widgets found",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(widgets) { widget ->
                        WidgetCard(
                            widget = widget,
                            onCardClick = { clickedWidget ->
                                onWidgetClick(clickedWidget)
                            },
                            onFavoriteToggle = onFavoriteToggle
                        )
                    }
                }
            }
        }
    }
} 