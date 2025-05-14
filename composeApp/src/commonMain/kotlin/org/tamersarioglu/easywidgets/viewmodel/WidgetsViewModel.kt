package org.tamersarioglu.easywidgets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.tamersarioglu.easywidgets.data.Widget
import org.tamersarioglu.easywidgets.data.WidgetCategory
import org.tamersarioglu.easywidgets.data.WidgetRepository

class WidgetsViewModel : ViewModel() {
    private val repository = WidgetRepository()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    
    private val _selectedCategory = MutableStateFlow<WidgetCategory?>(null)
    val selectedCategory: StateFlow<WidgetCategory?> = _selectedCategory
    
    val widgets = combine(
        repository.widgets,
        _searchQuery,
        _selectedCategory
    ) { widgets, query, category ->
        var filtered = widgets
        
        // Filter by category if selected
        if (category != null) {
            filtered = filtered.filter { it.category == category }
        }
        
        // Filter by search query
        if (query.isNotEmpty()) {
            filtered = filtered.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.description.contains(query, ignoreCase = true)
            }
        }
        
        filtered
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    
    val favoriteWidgets = repository.favoriteWidgets
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun selectCategory(category: WidgetCategory?) {
        _selectedCategory.value = category
    }
    
    fun toggleFavorite(widget: Widget) {
        repository.toggleFavorite(widget)
    }
} 