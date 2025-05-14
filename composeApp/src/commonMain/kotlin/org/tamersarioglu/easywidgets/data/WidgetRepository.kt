package org.tamersarioglu.easywidgets.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository managing widgets data
 */
class WidgetRepository {
    private val _widgets = MutableStateFlow(getInitialWidgets())
    val widgets: StateFlow<List<Widget>> = _widgets

    private val _favoriteWidgets = MutableStateFlow<List<Widget>>(emptyList())
    val favoriteWidgets: StateFlow<List<Widget>> = _favoriteWidgets

    init {
        updateFavorites()
    }

    fun toggleFavorite(widget: Widget) {
        _widgets.update { currentList ->
            currentList.map {
                if (it.name == widget.name) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
        updateFavorites()
    }

    private fun updateFavorites() {
        _favoriteWidgets.value = _widgets.value.filter { it.isFavorite }
    }

    fun getWidgetsByCategory(category: WidgetCategory): List<Widget> {
        return _widgets.value.filter { it.category == category }
    }

    companion object {
        fun getInitialWidgets(): List<Widget> {
            return listOf(
                // Basic Widgets
                Widget(
                    name = "Text",
                    category = WidgetCategory.BASIC,
                    description = "Displays text with various styling options",
                    codeSnippet = """
                        Text(
                            text = "Hello World",
                            color = Color.Blue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    """.trimIndent()
                ),
                Widget(
                    name = "Button",
                    category = WidgetCategory.BASIC,
                    description = "Interactive button with different styles",
                    codeSnippet = """
                        Button(onClick = { /* Do something */ }) {
                            Text("Click Me")
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Image",
                    category = WidgetCategory.BASIC,
                    description = "Displays images from various sources",
                    codeSnippet = """
                        Image(
                            painter = painterResource(id = R.drawable.sample),
                            contentDescription = "Sample Image"
                        )
                    """.trimIndent()
                ),
                Widget(
                    name = "Icon",
                    category = WidgetCategory.BASIC,
                    description = "Displays vector icons",
                    codeSnippet = """
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    """.trimIndent()
                ),
                Widget(
                    name = "Divider",
                    category = WidgetCategory.BASIC,
                    description = "Horizontal line that separates content",
                    codeSnippet = """
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    """.trimIndent()
                ),
                Widget(
                    name = "Spacer",
                    category = WidgetCategory.BASIC,
                    description = "Empty space with specified dimensions",
                    codeSnippet = """
                        // Horizontal spacer
                        Row {
                            Text("Left")
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Right")
                        }
                        
                        // Vertical spacer
                        Column {
                            Text("Top")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Bottom")
                        }
                    """.trimIndent()
                ),
                
                // Layout Widgets
                Widget(
                    name = "Column",
                    category = WidgetCategory.LAYOUT,
                    description = "Vertical arrangement of elements",
                    codeSnippet = """
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Item 1")
                            Text("Item 2")
                            Text("Item 3")
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Row",
                    category = WidgetCategory.LAYOUT,
                    description = "Horizontal arrangement of elements",
                    codeSnippet = """
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text("Left")
                            Text("Center")
                            Text("Right")
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Box",
                    category = WidgetCategory.LAYOUT,
                    description = "Stack elements on top of each other",
                    codeSnippet = """
                        Box(modifier = Modifier.size(100.dp)) {
                            Box(modifier = Modifier.matchParentSize().background(Color.Blue))
                            Text(
                                "Centered",
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White
                            )
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "LazyColumn",
                    category = WidgetCategory.LAYOUT,
                    description = "Vertical scrollable list with lazy loading",
                    codeSnippet = """
                        LazyColumn {
                            items(100) { index ->
                                Text(
                                    "Item #\index",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "LazyRow",
                    category = WidgetCategory.LAYOUT,
                    description = "Horizontal scrollable list with lazy loading",
                    codeSnippet = """
                        LazyRow {
                            items(20) { index ->
                                Card(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(8.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("\index")
                                    }
                                }
                            }
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Grid",
                    category = WidgetCategory.LAYOUT,
                    description = "Arrange items in a grid layout",
                    codeSnippet = """
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(9) { index ->
                                Card(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .aspectRatio(1f)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("\index")
                                    }
                                }
                            }
                        }
                    """.trimIndent()
                ),
                
                // Input Widgets
                Widget(
                    name = "TextField",
                    category = WidgetCategory.INPUT,
                    description = "Input field for text entry",
                    codeSnippet = """
                        var text by remember { mutableStateOf("") }
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Label") }
                        )
                    """.trimIndent()
                ),
                Widget(
                    name = "Checkbox",
                    category = WidgetCategory.INPUT,
                    description = "Boolean input control",
                    codeSnippet = """
                        var checked by remember { mutableStateOf(false) }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { checked = it }
                            )
                            Text("Check me")
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "RadioButton",
                    category = WidgetCategory.INPUT,
                    description = "Selection control for mutually exclusive options",
                    codeSnippet = """
                        val radioOptions = listOf("Option 1", "Option 2", "Option 3")
                        var selectedOption by remember { mutableStateOf(radioOptions[0]) }
                        
                        Column {
                            radioOptions.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = (option == selectedOption),
                                        onClick = { selectedOption = option }
                                    )
                                    Text(
                                        text = option,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Switch",
                    category = WidgetCategory.INPUT,
                    description = "Toggle control for on/off states",
                    codeSnippet = """
                        var switched by remember { mutableStateOf(false) }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Enable feature")
                            Switch(
                                checked = switched,
                                onCheckedChange = { switched = it }
                            )
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Slider",
                    category = WidgetCategory.INPUT,
                    description = "Control for selecting a value from a range",
                    codeSnippet = """
                        var sliderValue by remember { mutableStateOf(0f) }
                        Column {
                            Slider(
                                value = sliderValue,
                                onValueChange = { sliderValue = it },
                                valueRange = 0f..100f,
                                steps = 10
                            )
                            Text("Value: \{sliderValue.toInt()}")
                        }
                    """.trimIndent()
                ),
                
                // Container Widgets
                Widget(
                    name = "Card",
                    category = WidgetCategory.CONTAINER,
                    description = "Container with elevation and rounded corners",
                    codeSnippet = """
                        Card(
                            modifier = Modifier.padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
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
                    """.trimIndent()
                ),
                Widget(
                    name = "Surface",
                    category = WidgetCategory.CONTAINER,
                    description = "Basic container with material styling",
                    codeSnippet = """
                        Surface(
                            modifier = Modifier.size(100.dp),
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("Surface", color = Color.White)
                            }
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Dialog",
                    category = WidgetCategory.CONTAINER,
                    description = "Modal window for user interactions",
                    codeSnippet = """
                        var showDialog by remember { mutableStateOf(false) }
                        
                        Button(onClick = { showDialog = true }) {
                            Text("Show Dialog")
                        }
                        
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Dialog Title") },
                                text = { Text("This is the content of the dialog.") },
                                confirmButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "BottomSheet",
                    category = WidgetCategory.CONTAINER,
                    description = "Panel that slides up from the bottom edge",
                    codeSnippet = """
                        var showBottomSheet by remember { mutableStateOf(false) }
                        
                        Button(onClick = { showBottomSheet = true }) {
                            Text("Show Bottom Sheet")
                        }
                        
                        if (showBottomSheet) {
                            ModalBottomSheet(
                                onDismissRequest = { showBottomSheet = false },
                                sheetState = rememberModalBottomSheetState()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        "Bottom Sheet Title", 
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("This is content inside the bottom sheet.")
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { showBottomSheet = false },
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Text("Close")
                                    }
                                }
                            }
                        }
                    """.trimIndent()
                ),
                
                // Advanced Widgets
                Widget(
                    name = "TabRow",
                    category = WidgetCategory.ADVANCED,
                    description = "Row of tabs to navigate between related content",
                    codeSnippet = """
                        var selectedTabIndex by remember { mutableStateOf(0) }
                        val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
                        
                        Column {
                            TabRow(selectedTabIndex = selectedTabIndex) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = selectedTabIndex == index,
                                        onClick = { selectedTabIndex = index },
                                        text = { Text(title) }
                                    )
                                }
                            }
                            
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)) {
                                Text("Content for tab \{tabs[selectedTabIndex]}")
                            }
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Pager",
                    category = WidgetCategory.ADVANCED,
                    description = "Horizontal pager for swipeable content",
                    codeSnippet = """
                        val pagerState = rememberPagerState(pageCount = { 3 })
                        
                        Column {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.weight(1f)
                            ) { page ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = when (page) {
                                            0 -> MaterialTheme.colorScheme.primary
                                            1 -> MaterialTheme.colorScheme.secondary
                                            else -> MaterialTheme.colorScheme.tertiary
                                        }
                                    )
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            "Page \{page + 1}",
                                            style = MaterialTheme.typography.headlineLarge,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                            
                            HorizontalPagerIndicator(
                                pagerState = pagerState,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(16.dp)
                            )
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "PullRefresh",
                    category = WidgetCategory.ADVANCED,
                    description = "Pull-to-refresh functionality for content",
                    codeSnippet = """
                        var refreshing by remember { mutableStateOf(false) }
                        var itemCount by remember { mutableStateOf(15) }
                        
                        LaunchedEffect(refreshing) {
                            if (refreshing) {
                                delay(1500)
                                itemCount += 5
                                refreshing = false
                            }
                        }
                        
                        PullRefreshIndicator(
                            refreshing = refreshing,
                            state = rememberPullRefreshState(
                                refreshing = refreshing,
                                onRefresh = { refreshing = true }
                            ),
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                        
                        LazyColumn {
                            items(itemCount) { index ->
                                ListItem(
                                    headlineContent = { Text("Item \{index + 1}") },
                                    supportingContent = { Text("Pull to refresh to load more") }
                                )
                                Divider()
                            }
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "Animation",
                    category = WidgetCategory.ADVANCED,
                    description = "Animated UI components",
                    codeSnippet = """
                        var expanded by remember { mutableStateOf(false) }
                        val transitionData = updateTransition(targetState = expanded, label = "expansion")
                        
                        val size by transitionData.animateDp(
                            label = "size",
                            targetValueByState = { if (it) 200.dp else 100.dp }
                        )
                        val color by transitionData.animateColor(
                            label = "color",
                            targetValueByState = { 
                                if (it) MaterialTheme.colorScheme.primary 
                                else MaterialTheme.colorScheme.secondary 
                            }
                        )
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(size)
                                    .background(color)
                                    .clickable { expanded = !expanded }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Tap the box to animate")
                        }
                    """.trimIndent()
                ),
                Widget(
                    name = "CustomComposable",
                    category = WidgetCategory.ADVANCED,
                    description = "Custom composable function example",
                    codeSnippet = """
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
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = text,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        
                        // Usage:
                        GradientButton(
                            text = "Custom Button",
                            onClick = { /* Do something */ },
                            modifier = Modifier.fillMaxWidth()
                        )
                    """.trimIndent()
                )
            )
        }
    }
} 