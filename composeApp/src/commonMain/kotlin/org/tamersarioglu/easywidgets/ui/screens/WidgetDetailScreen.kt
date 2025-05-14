package org.tamersarioglu.easywidgets.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.ui.components.CodeSnippet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetDetailScreen(
    widget: Widget,
    onBackClick: () -> Unit,
    onFavoriteToggle: (Widget) -> Unit
) {
    println("WidgetDetailScreen composable called for ${widget.name}")
    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(widget.name) },
                navigationIcon = {
                    IconButton(onClick = { 
                        println("Back button pressed in WidgetDetailScreen")
                        onBackClick() 
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onFavoriteToggle(widget) }) {
                        Icon(
                            imageVector = if (widget.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (widget.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (widget.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = widget.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Category: ${widget.category.title}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = widget.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    WidgetPreview(widget)
                }

                HorizontalDivider(
                    Modifier.padding(vertical = 16.dp),
                    DividerDefaults.Thickness,
                    DividerDefaults.color
                )

                Text(
                    text = "Code Snippet",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                CodeSnippet(
                    code = widget.codeSnippet,
                    onCopyClick = { code ->
                        clipboardManager.setText(AnnotatedString(code))
                    }
                )
            }
        }
    }
}

@Composable
fun WidgetPreview(widget: Widget) {
    when (widget.name) {
        "Text" -> {
            Text(
                text = "Hello World",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(16.dp)
            )
        }
        "Button" -> {
            Button(
                onClick = { /* Do nothing in preview */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Click Me")
            }
        }
        "Card" -> {
            Card(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Card Title",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Card content goes here",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        "Row" -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Left")
                Spacer(modifier = Modifier.width(16.dp))
                Text("Center")
                Spacer(modifier = Modifier.width(16.dp))
                Text("Right")
            }
        }
        else -> {
            Text(
                text = "Preview not available for ${widget.name}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 