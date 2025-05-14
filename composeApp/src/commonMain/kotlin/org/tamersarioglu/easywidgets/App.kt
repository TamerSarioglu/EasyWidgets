package org.tamersarioglu.easywidgets

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tamersarioglu.easywidgets.ui.navigation.AppNavigation
import org.tamersarioglu.easywidgets.ui.theme.EasyWidgetsTheme
import org.tamersarioglu.easywidgets.viewmodel.WidgetsViewModel

@Composable
@Preview
fun App() {
    val viewModel = WidgetsViewModel()
    
    EasyWidgetsTheme {
        AppNavigation(viewModel = viewModel)
    }
}