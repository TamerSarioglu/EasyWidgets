package org.tamersarioglu.easywidgets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
    
    // Create a Navigator that will trigger recomposition when the screen changes
    val navigator = remember { Navigator<Screen>(Screen.Home) }
    val currentScreen = navigator.currentScreen
    
    println("Current screen: $currentScreen")
    
    // Use a when statement to handle different screen types
    when (val screen = currentScreen) {
        is Screen.Home, is Screen.Favorites, is Screen.Category -> {
            AppDrawer(
                currentScreen = currentScreen,
                onHomeClick = { navigator.navigateTo(Screen.Home) },
                onFavoritesClick = { navigator.navigateTo(Screen.Favorites) },
                onCategoryClick = { category -> navigator.navigateTo(Screen.Category(category)) }
            ) {
                when (screen) {
                    is Screen.Home -> {
                        HomeScreen(
                            widgets = widgets,
                            onWidgetClick = { widget -> 
                                println("HomeScreen: onWidgetClick called with ${widget.name}")
                                navigator.navigateTo(Screen.WidgetDetail(widget))
                            },
                            onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) },
                            onCategorySelected = { category -> viewModel.selectCategory(category) },
                            onSearchQueryChanged = { query -> viewModel.setSearchQuery(query) }
                        )
                    }
                    is Screen.Favorites -> {
                        FavoritesScreen(
                            favoriteWidgets = favoriteWidgets,
                            onWidgetClick = { widget -> 
                                println("FavoritesScreen: onWidgetClick called with ${widget.name}")
                                navigator.navigateTo(Screen.WidgetDetail(widget))
                            },
                            onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) }
                        )
                    }
                    is Screen.Category -> {
                        CategoryScreen(
                            category = screen.category,
                            widgets = widgets.filter { it.category == screen.category },
                            onWidgetClick = { widget -> 
                                println("CategoryScreen: onWidgetClick called with ${widget.name}")
                                navigator.navigateTo(Screen.WidgetDetail(widget))
                            },
                            onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) },
                            onSearchQueryChanged = { query -> viewModel.setSearchQuery(query) }
                        )
                    }
                    else -> {}
                }
            }
        }
        is Screen.WidgetDetail -> {
            println("Rendering WidgetDetailScreen for ${screen.widget.name}")
            // For widget detail, don't use the drawer
            WidgetDetailScreen(
                widget = screen.widget,
                onBackClick = { 
                    println("Back button clicked")
                    navigator.navigateBack() 
                },
                onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) }
            )
        }
    }
}

class Navigator<T>(initialScreen: T) {
    // Use mutableStateOf to ensure recomposition when the screen changes
    private var _screenStack = mutableStateOf(mutableListOf(initialScreen))
    
    val currentScreen: T
        get() = _screenStack.value.last()
    
    fun navigateTo(screen: T) {
        println("Navigating to: $screen")
        val currentStack = _screenStack.value
        if (currentStack.lastOrNull() != screen) {
            val newStack = currentStack.toMutableList()
            newStack.add(screen)
            _screenStack.value = newStack
            println("Screen stack size: ${_screenStack.value.size}")
        } else {
            println("Screen already at the top of stack, not navigating")
        }
    }
    
    fun navigateBack(): Boolean {
        val currentStack = _screenStack.value
        if (currentStack.size > 1) {
            val newStack = currentStack.toMutableList()
            newStack.removeAt(newStack.lastIndex)
            _screenStack.value = newStack
            println("Navigating back, new screen: ${_screenStack.value.last()}")
            return true
        }
        println("Cannot navigate back, stack size: ${currentStack.size}")
        return false
    }
} 