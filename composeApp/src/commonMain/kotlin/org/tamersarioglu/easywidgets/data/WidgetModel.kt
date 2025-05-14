package org.tamersarioglu.easywidgets.data

/**
 * Represents a category of widgets
 */
enum class WidgetCategory(val title: String) {
    BASIC("Basic"),
    LAYOUT("Layout"),
    INPUT("Input"),
    CONTAINER("Container"),
    ADVANCED("Advanced")
}

/**
 * Represents a widget in the showcase
 */
data class Widget(
    val name: String,
    val category: WidgetCategory,
    val description: String,
    val codeSnippet: String,
    val isFavorite: Boolean = false
)

/**
 * Represents a specific example of a widget
 */
data class WidgetExample(
    val title: String,
    val description: String,
    val codeSnippet: String,
    val parentWidget: Widget
) 