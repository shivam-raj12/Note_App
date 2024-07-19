package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shivam_raj.noteapp.LottieAnimations
import com.shivam_raj.noteapp.R

@Composable
fun EmptySearchResult(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sorry!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
        LottieAnimations(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            animation = R.raw.empty_search_result
        )
        Text(
            text = "We couldn't find what you're looking for",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .alpha(0.7f),
        )
    }
}