package org.tamersarioglu.easywidgets.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CodeSnippet(
    code: String,
    onCopyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Code",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onCopyClick(code) }) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = "Copy code"
                    )
                }
            }
            
            Text(
                text = applySyntaxHighlighting(code),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Start
                )
            )
        }
    }
}

private fun applySyntaxHighlighting(code: String): AnnotatedString {
    return buildAnnotatedString {
        append(code)
        val keywords = listOf("val", "var", "fun", "class", "object", "interface", "if", "else", "when", "by", "import")
        keywords.forEach { keyword ->
            val regex = "\\b$keyword\\b".toRegex()
            val matches = regex.findAll(code)
            matches.forEach { match ->
                addStyle(
                    style = SpanStyle(color = Color(0xFF7B1FA2)), // Purple
                    start = match.range.first,
                    end = match.range.last + 1
                )
            }
        }

        val stringRegex = "\"[^\"]*\"".toRegex()
        val stringMatches = stringRegex.findAll(code)
        stringMatches.forEach { match ->
            addStyle(
                style = SpanStyle(color = Color(0xFF689F38)), // Green
                start = match.range.first,
                end = match.range.last + 1
            )
        }

        val commentRegex = "//.*".toRegex()
        val commentMatches = commentRegex.findAll(code)
        commentMatches.forEach { match ->
            addStyle(
                style = SpanStyle(color = Color(0xFF9E9E9E)), // Grey
                start = match.range.first,
                end = match.range.last + 1
            )
        }
    }
} 