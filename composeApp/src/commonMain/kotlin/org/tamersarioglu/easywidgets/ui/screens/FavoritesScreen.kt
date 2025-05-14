package org.tamersarioglu.easywidgets.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.ui.components.WidgetCard

@Composable
fun FavoritesScreen(
    favoriteWidgets: List<Widget>,
    onWidgetClick: (Widget) -> Unit,
    onFavoriteToggle: (Widget) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (favoriteWidgets.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No favorites yet",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Text(
                    text = "Add widgets to your favorites by clicking the heart icon",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Favorites",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
                
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(favoriteWidgets) { widget ->
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