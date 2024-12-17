package com.t8rin.animation.samples.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimationDurationSlider(
    duration: Int,
    onValueChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
                start = 16.dp
            )
    ) {
        Text(
            text = "Длительность анимации: $duration мс",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Spacer(Modifier.height(12.dp))
        Slider(
            value = duration.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 500f..5000f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.tertiary,
                activeTrackColor = MaterialTheme.colorScheme.tertiary,
                inactiveTrackColor = MaterialTheme.colorScheme.tertiary.copy(0.2f),
                inactiveTickColor = MaterialTheme.colorScheme.tertiary
            )
        )
    }
}