package org.tamersarioglu.easywidgets.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.tamersarioglu.easywidgets.data.WidgetCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentScreen: Screen,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onCategoryClick: (WidgetCategory) -> Unit,
    onExamplesGalleryClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    fun closeDrawer() {
        scope.launch { drawerState.close() }
    }
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerItem(
                    title = "Home",
                    selected = currentScreen is Screen.Home,
                    icon = Icons.Filled.Home,
                    onClick = {
                        onHomeClick()
                        closeDrawer()
                    }
                )
                DrawerItem(
                    title = "Favorites",
                    selected = currentScreen is Screen.Favorites,
                    icon = Icons.Filled.Favorite,
                    onClick = {
                        onFavoritesClick()
                        closeDrawer()
                    }
                )
                
                // Examples Gallery
                DrawerItem(
                    title = "Examples Gallery",
                    selected = currentScreen is Screen.ExamplesGallery,
                    icon = Icons.Filled.ViewModule,
                    onClick = {
                        onExamplesGalleryClick()
                        closeDrawer()
                    }
                )

                HorizontalDivider(
                    Modifier.padding(vertical = 8.dp),
                    DividerDefaults.Thickness,
                    DividerDefaults.color
                )
                
                Text(
                    text = "Widget Categories",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
                
                WidgetCategory.entries.forEach { category ->
                    val selected = when (currentScreen) {
                        is Screen.Category -> currentScreen.category == category
                        else -> false
                    }
                    DrawerItem(
                        title = category.title,
                        selected = selected,
                        onClick = {
                            onCategoryClick(category)
                            closeDrawer()
                        }
                    )
                }
            }
        }
    ) {
        AppScaffold(
            currentScreen = currentScreen,
            onDrawerClick = { scope.launch { drawerState.open() } },
            content = content
        )
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "EasyWidgets",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun DrawerItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector? = null
) {
    val background = if (selected) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.surface
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(background)
            .clickable { onClick() },
        color = background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    currentScreen: Screen,
    onDrawerClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            is Screen.Home -> "EasyWidgets"
                            is Screen.Favorites -> "Favorites"
                            is Screen.Category -> currentScreen.category.title
                            is Screen.WidgetDetail -> currentScreen.widget.name
                            is Screen.ExamplesGallery -> "Examples Gallery"
                            is Screen.ExampleDetail -> "${currentScreen.exampleName} Example"
                        }
                    )
                },
                navigationIcon = {
                    if (currentScreen is Screen.WidgetDetail || 
                       currentScreen is Screen.ExamplesGallery || 
                       currentScreen is Screen.ExampleDetail) {
                        // Don't show drawer menu for detail screens - navigation is handled in their screens
                    } else {
                        IconButton(onClick = onDrawerClick) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
} 