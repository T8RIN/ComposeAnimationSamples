package com.t8rin.animation.samples.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.t8rin.animation.samples.ui.components.model.AnimationType

@Composable
fun AnimationExample(
    animationType: AnimationType,
    duration: Int
) {
    AnimatedContent(
        targetState = duration,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { duration ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            animationType.content(duration)
        }
    }
}