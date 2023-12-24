package com.example.jetreader.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(15.dp),
        text = "A. Reader",
        style = MaterialTheme.typography.headlineLarge,
        color = Color.Red.copy(0.5f)
    )
}