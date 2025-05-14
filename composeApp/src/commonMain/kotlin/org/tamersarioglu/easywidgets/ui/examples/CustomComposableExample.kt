package org.tamersarioglu.easywidgets.ui.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomComposableExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Custom Composable Examples",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Example 1: Gradient Button
        GradientButton(
            text = "Gradient Button",
            onClick = { /* Handle click */ },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Example 2: Gradient Button with custom colors
        GradientButton(
            text = "Custom Colors",
            onClick = { /* Handle click */ },
            startColor = Color(0xFF00BCD4),
            endColor = Color(0xFF03A9F4),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Example 3: Elevated Color Card
        ElevatedColorCard(
            title = "Elevated Card",
            content = "This is a custom card with elevation and gradient background",
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Example 4: Another Custom Card
        ElevatedColorCard(
            title = "Another Style",
            content = "Customize cards with different colors and elevation",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    startColor: Color = Color(0xFF6200EE),
    endColor: Color = Color(0xFF3700B3),
    cornerRadius: Dp = 16.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(startColor, endColor)
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun ElevatedColorCard(
    title: String,
    content: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .shadow(elevation, RoundedCornerShape(16.dp))
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 