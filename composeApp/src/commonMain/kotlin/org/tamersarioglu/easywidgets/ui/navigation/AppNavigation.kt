package org.tamersarioglu.easywidgets.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.data.WidgetCategory
import org.tamersarioglu.easywidgets.ui.screens.ExampleDetailScreen
import org.tamersarioglu.easywidgets.ui.screens.ExamplesGalleryScreen
import org.tamersarioglu.easywidgets.ui.screens.FavoritesScreen
import org.tamersarioglu.easywidgets.ui.screens.HomeScreen
import org.tamersarioglu.easywidgets.ui.screens.WidgetDetailScreen
import org.tamersarioglu.easywidgets.viewmodel.WidgetsViewModel

sealed class Screen {
    data object Home : Screen()
    data object Favorites : Screen()
    data object ExamplesGallery : Screen()
    data class WidgetDetail(val widget: Widget) : Screen()
    data class ExampleDetail(val exampleName: String) : Screen()
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: WidgetsViewModel) {
    val widgets by viewModel.widgets.collectAsState()
    val favoriteWidgets by viewModel.favoriteWidgets.collectAsState()
    
    // Create a Navigator that will trigger recomposition when the screen changes
    val navigator = remember { Navigator<Screen>(Screen.Home) }
    val currentScreen = navigator.currentScreen
    
    // Define bottom navigation items
    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, Screen.Home),
        BottomNavItem("Favorites", Icons.Default.Favorite, Screen.Favorites),
        BottomNavItem("Examples", Icons.Default.ViewModule, Screen.ExamplesGallery)
    )
    
    // Determine if we should show bottom nav (don't show for detail screens)
    val showBottomNav = currentScreen is Screen.Home || 
                        currentScreen is Screen.Favorites || 
                        currentScreen is Screen.ExamplesGallery
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            is Screen.Home -> "EasyWidgets"
                            is Screen.Favorites -> "Favorites"
                            is Screen.ExamplesGallery -> "Examples"
                            is Screen.WidgetDetail -> currentScreen.widget.name
                            is Screen.ExampleDetail -> currentScreen.exampleName
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = when (item.screen) {
                            is Screen.Home -> currentScreen is Screen.Home
                            is Screen.Favorites -> currentScreen is Screen.Favorites
                            is Screen.ExamplesGallery -> currentScreen is Screen.ExamplesGallery
                            else -> false
                        }
                        
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = selected,
                            onClick = { 
                                if (currentScreen != item.screen) {
                                    navigator.navigateTo(item.screen)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (val screen = currentScreen) {
                    is Screen.Home -> {
                        HomeScreen(
                            widgets = widgets,
                            onWidgetClick = { widget ->
                                navigator.navigateTo(Screen.WidgetDetail(widget))
                            },
                            onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) },
                            onSearchQueryChanged = { query -> viewModel.setSearchQuery(query) },
                            onExamplesGalleryClick = { 
                                navigator.navigateTo(Screen.ExamplesGallery)
                            }
                        )
                    }
                    is Screen.Favorites -> {
                        FavoritesScreen(
                            favoriteWidgets = favoriteWidgets,
                            onWidgetClick = { widget -> 
                                navigator.navigateTo(Screen.WidgetDetail(widget))
                            },
                            onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) }
                        )
                    }
                    is Screen.ExamplesGallery -> {
                        ExamplesGalleryScreen(
                            onBackClick = { 
                                // For bottom navigation, go to Home when pressing back from Examples
                                navigator.navigateTo(Screen.Home) 
                            },
                            onExampleClick = { exampleName -> navigator.navigateTo(Screen.ExampleDetail(exampleName)) }
                        )
                    }
                    is Screen.WidgetDetail -> {
                        WidgetDetailScreen(
                            widget = screen.widget,
                            onBackClick = { navigator.navigateBack() },
                            onFavoriteToggle = { widget -> viewModel.toggleFavorite(widget) }
                        )
                    }
                    is Screen.ExampleDetail -> {
                        ExampleDetailScreen(
                            exampleName = screen.exampleName,
                            onBackClick = { navigator.navigateBack() }
                        )
                    }
                }
            }
        }
    }
}

class Navigator<T>(initialScreen: T) {
    // Use mutableStateOf to ensure recomposition when the screen changes
    private var _screenStack = mutableStateOf(mutableListOf(initialScreen))
    
    val currentScreen: T
        get() = _screenStack.value.last()
    
    fun navigateTo(screen: T) {
        val currentStack = _screenStack.value
        if (currentStack.lastOrNull() != screen) {
            val newStack = currentStack.toMutableList()
            newStack.add(screen)
            _screenStack.value = newStack
        }
    }
    
    fun navigateBack(): Boolean {
        val currentStack = _screenStack.value
        if (currentStack.size > 1) {
            val newStack = currentStack.toMutableList()
            newStack.removeAt(newStack.lastIndex)
            _screenStack.value = newStack
            return true
        }
        return false
    }
} 