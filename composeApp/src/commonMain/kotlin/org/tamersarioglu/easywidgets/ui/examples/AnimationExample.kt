package org.tamersarioglu.easywidgets.ui.examples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimationExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Animation Examples",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Color Animation
        var colorChanged by remember { mutableStateOf(false) }
        val backgroundColor by animateColorAsState(
            targetValue = if (colorChanged) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary, 
            animationSpec = tween(durationMillis = 1000),
            label = "colorAnimation"
        )
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .clickable { colorChanged = !colorChanged }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Tap to animate color",
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Size Animation
        var expanded by remember { mutableStateOf(false) }
        val size by animateDpAsState(
            targetValue = if (expanded) 150.dp else 80.dp, 
            animationSpec = tween(durationMillis = 500),
            label = "sizeAnimation"
        )
        val roundCorners by animateDpAsState(
            targetValue = if (expanded) 16.dp else 40.dp,
            label = "cornerAnimation"
        )
        
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(roundCorners))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { expanded = !expanded },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (expanded) "Shrink" else "Grow",
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Visibility Animation
        var visible by remember { mutableStateOf(true) }
        
        Button(
            onClick = { visible = !visible },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (visible) "Hide Content" else "Show Content")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "This content animates in and out",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AnimatedDot(MaterialTheme.colorScheme.primary, 0)
                    AnimatedDot(MaterialTheme.colorScheme.secondary, 100)
                    AnimatedDot(MaterialTheme.colorScheme.tertiary, 200)
                }
            }
        }
    }
}

@Composable
fun AnimatedDot(color: Color, delayMillis: Int) {
    // Pulsating animation
    var pulsing by remember { mutableStateOf(true) }
    val scale by animateFloatAsState(
        targetValue = if (pulsing) 1.2f else 0.8f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing,
            delayMillis = delayMillis
        ),
        finishedListener = { pulsing = !pulsing },
        label = "pulseAnimation"
    )
    
    Box(
        modifier = Modifier
            .size(24.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(color)
    )
} 