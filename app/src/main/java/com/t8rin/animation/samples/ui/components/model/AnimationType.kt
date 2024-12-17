package com.t8rin.animation.samples.ui.components.model

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.isFinished
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

sealed class AnimationType(
    val code: String,
    val content: @Composable (duration: Int) -> Unit
) {
    data object InfiniteTransition : AnimationType(
        code = """
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = duration),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(
                Modifier
                    .size(100.dp)
                    .scale(scale)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )
        """.trimIndent(),
        content = { duration ->
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = duration),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(
                Modifier
                    .size(100.dp)
                    .scale(scale)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )
        }
    )

    data object AnimatedContent : AnimationType(
        code = """
            var counter by remember { mutableIntStateOf(0) }
            AnimatedContent(
                targetState = counter,
                transitionSpec = {
                    fadeIn(tween(duration)) + expandVertically() togetherWith fadeOut(tween(duration)) + shrinkVertically()
                }
            ) { cnt ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { counter++ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нажато ${"\$cnt"} раз",
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        """.trimIndent(),
        content = { duration ->
            var counter by remember { mutableIntStateOf(0) }
            androidx.compose.animation.AnimatedContent(
                targetState = counter,
                transitionSpec = {
                    fadeIn(tween(duration)) + expandVertically() togetherWith fadeOut(tween(duration)) + shrinkVertically()
                }
            ) { cnt ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { counter++ }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нажато $cnt раз",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )

    data object AnimatedVisibility : AnimationType(
        code = """
            AnimatedVisibility(visible = visible) {
                Box(
                    Modifier
                        .size(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        """.trimIndent(),
        content = { duration ->
            var visible by remember { mutableStateOf(true) }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(duration.toLong())
                    visible = !visible
                }
            }
            androidx.compose.animation.AnimatedVisibility(visible = visible) {
                Box(
                    Modifier
                        .size(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    )

    data object AnimateContentSize : AnimationType(
        code = """
            Box(
                Modifier
                    .animateContentSize()
                    .size(if (expanded) 200.dp else 100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        """.trimIndent(),
        content = { duration ->
            var expanded by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(duration.toLong())
                    expanded = !expanded
                }
            }
            Box(
                Modifier
                    .animateContentSize()
                    .size(if (expanded) 200.dp else 100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
            )
        }
    )

    data object AnimateAsState : AnimationType(
        code = """
            val size by animateDpAsState(
                targetValue = if (big) 150.dp else 100.dp,
                animationSpec = tween(durationMillis = duration)
            )
            Box(
                Modifier
                    .size(size)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        """.trimIndent(),
        content = { duration ->
            var big by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(duration.toLong())
                    big = !big
                }
            }
            val size by animateDpAsState(
                targetValue = if (big) 150.dp else 100.dp,
                animationSpec = tween(durationMillis = duration)
            )
            Box(
                Modifier
                    .size(size)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    )

    data object UpdateTransition : AnimationType(
        code = """
            val transition = updateTransition(toggled, label = "Transition")
            val color by transition.animateColor(
                transitionSpec = { tween(durationMillis = duration) },
                label = "Color"
            ) {
                if (it) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            }
            Box(
                Modifier
                    .size(100.dp)
                    .background(color, RoundedCornerShape(8.dp))
            )
        """.trimIndent(),
        content = { duration ->
            var toggled by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(duration.toLong())
                    toggled = !toggled
                }
            }

            val transition = updateTransition(toggled, label = "Transition")
            val color by transition.animateColor(
                transitionSpec = { tween(durationMillis = duration) },
                label = "Color"
            ) {
                if (it) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            }
            Box(
                Modifier
                    .size(100.dp)
                    .background(color, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            )
        }
    )

    data object Animatable : AnimationType(
        code = """
            val animatable = remember { Animatable(0f) }
            LaunchedEffect(duration) {
                while (true) {
                    animatable.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = duration)
                    )
                    delay(400)
                    animatable.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = duration)
                    )
                }
            }
            Box(
                Modifier
                    .size((100 * animatable.value).dp)
                    .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
            )
        """.trimIndent(),
        content = { duration ->
            val animatable = remember { Animatable(0f) }
            LaunchedEffect(duration) {
                while (true) {
                    animatable.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = duration)
                    )
                    delay(400)
                    animatable.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = duration)
                    )
                }
            }
            Box(
                Modifier
                    .size((100 * animatable.value).dp)
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            )
        }
    )

    data object AnimationState : AnimationType(
        code = """
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp / 2 - 64.dp
            var targetValue by rememberSaveable {
                mutableFloatStateOf(-1f)
            }
            val animationState = remember { AnimationState(targetValue) }

            LaunchedEffect(targetValue) {
                animationState.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(duration),
                    sequentialAnimation = !animationState.isFinished
                )
                targetValue = if(targetValue == -1f) 1f else -1f
            }

            Box(
                Modifier
                    .size(100.dp)
                    .offset(
                        x = screenWidth * animationState.value
                    )
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            )
        """.trimIndent(),
        content = { duration ->
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp / 2 - 64.dp
            var targetValue by rememberSaveable {
                mutableFloatStateOf(-1f)
            }
            val animationState = remember { AnimationState(targetValue) }

            LaunchedEffect(targetValue) {
                animationState.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(duration),
                    sequentialAnimation = !animationState.isFinished
                )
                targetValue = if(targetValue == -1f) 1f else -1f
            }

            Box(
                Modifier
                    .size(100.dp)
                    .offset(
                        x = screenWidth * animationState.value
                    )
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            )
        }
    )

    data object AnimateItemPlacement : AnimationType(
        code = """
            val data = remember {
                SnapshotStateList<Pair<Color, Int>>().apply {
                    repeat(20) {
                        add(Color(Random.nextInt()) to it + 1)
                    }
                }
            }
            LazyColumn {
                items(
                    items = data,
                    key = { it.hashCode() }
                ) { (color, id) ->
                    Box(
                        modifier = Modifier
                            .background(color)
                            .fillMaxWidth()
                            .height(40.dp)
                            .animateItem(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(id.toString())
                    }
                }
            }
        """.trimIndent(),
        content = { duration ->
            val data = remember {
                SnapshotStateList<Pair<Color, Int>>().apply {
                    repeat(20) {
                        add(Color(Random.nextInt()) to it + 1)
                    }
                }
            }
            LaunchedEffect(Unit) {
                while (true) {
                    data.shuffle()
                    delay(1500)
                }
            }
            LazyColumn(
                modifier = Modifier.height(200.dp)
            ) {
                items(
                    items = data,
                    key = { it.hashCode() }
                ) { (color, id) ->
                    Box(
                        modifier = Modifier
                            .background(color)
                            .fillMaxWidth()
                            .height(40.dp)
                            .animateItem(
                                fadeInSpec = tween(duration)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(id.toString())
                    }
                }
            }
        }
    )
}