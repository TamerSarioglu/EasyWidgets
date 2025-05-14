package org.tamersarioglu.easywidgets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.data.WidgetCategory
import org.tamersarioglu.easywidgets.ui.screens.CategoryScreen
import org.tamersarioglu.easywidgets.ui.screens.FavoritesScreen
import org.tamersarioglu.easywidgets.ui.screens.HomeScreen
import org.tamersarioglu.easywidgets.ui.screens.WidgetDetailScreen
import org.tamersarioglu.easywidgets.viewmodel.WidgetsViewModel

sealed class Screen {
    data object Home : Screen()
    data object Favorites : Screen()
    data class Category(val category: WidgetCategory) : Screen()
    data class WidgetDetail(val widget: Widget) : Screen()
}

@Composable
fun AppNavigation(viewModel: WidgetsViewModel) {
    val widgets by viewModel.widgets.collectAsState()
    val favoriteWidgets by viewModel.favoriteWidgets.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    
    val navigator = remember { Navigator<Screen>(Screen.Home) }
    val currentScreen = navigator.currentScreen
    
    AppDrawer(
        currentScreen = currentScreen,
        onHomeClick = { navigator.navigateTo(Screen.Home) },
        onFavoritesClick = { navigator.navigateTo(Screen.Favorites) },
        onCategoryClick = { category -> navigator.navigateTo(Screen.Category(category)) }
    ) {
        when (val screen = currentScreen) {
            is Screen.Home -> {
                HomeScreen(
                    widgets = widgets,
                    onWidgetClick = { widget -> navigator.navigateTo(Screen.WidgetDetail(widget)) },
                    onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) },
                    onCategorySelected = { category -> viewModel.selectCategory(category) },
                    onSearchQueryChanged = { query -> viewModel.setSearchQuery(query) }
                )
            }
            is Screen.Favorites -> {
                FavoritesScreen(
                    favoriteWidgets = favoriteWidgets,
                    onWidgetClick = { widget -> navigator.navigateTo(Screen.WidgetDetail(widget)) },
                    onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) }
                )
            }
            is Screen.Category -> {
                CategoryScreen(
                    category = screen.category,
                    widgets = widgets.filter { it.category == screen.category },
                    onWidgetClick = { widget -> navigator.navigateTo(Screen.WidgetDetail(widget)) },
                    onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) },
                    onSearchQueryChanged = { query -> viewModel.setSearchQuery(query) }
                )
            }
            is Screen.WidgetDetail -> {
                WidgetDetailScreen(
                    widget = screen.widget,
                    onBackClick = { navigator.navigateBack() },
                    onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) }
                )
            }
        }
    }
}

class Navigator<T>(initialScreen: T) {
    private val screenStack = mutableListOf(initialScreen)
    
    val currentScreen: T
        get() = screenStack.last()
    
    fun navigateTo(screen: T) {
        screenStack.add(screen)
    }
    
    fun navigateBack(): Boolean {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.lastIndex)
            return true
        }
        return false
    }
} 