package com.t8rin.animation.samples.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.t8rin.animation.samples.ui.components.AnimationDurationSlider
import com.t8rin.animation.samples.ui.components.AnimationExample
import com.t8rin.animation.samples.ui.components.CodeExample
import com.t8rin.animation.samples.ui.components.model.Question
import com.t8rin.animation.samples.ui.viewModel.MainViewModel
import com.t8rin.animation.samples.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: MainViewModel) {
    val currentQuestion = viewModel.navigationStack.last()
    var duration by remember { mutableIntStateOf(1000) }

    BackHandler(
        enabled = currentQuestion != Question.Start,
        onBack = viewModel::pop
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.animation_samples)) },
                navigationIcon = {
                    AnimatedVisibility(currentQuestion != Question.Start) {
                        IconButton(onClick = viewModel::pop) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(currentQuestion != Question.Start) {
                        IconButton(onClick = viewModel::popAll) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                AnimatedContent(currentQuestion.questionResId) { questionResId ->
                    Text(
                        text = stringResource(questionResId),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedContent(
                targetState = currentQuestion,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { currentQuestion ->
                val animationType = currentQuestion.animationType

                if (currentQuestion !is Question.AnswerNotFound) {
                    if (animationType == null) {
                        Row {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                onClick = {
                                    currentQuestion.noPath?.let(viewModel::push)
                                },
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.no),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                onClick = {
                                    currentQuestion.yesPath?.let(viewModel::push)
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.yes),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        Column {
                            Spacer(modifier = Modifier.height(30.dp))
                            AnimationExample(
                                animationType = animationType,
                                duration = duration
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            AnimationDurationSlider(
                                duration = duration,
                                onValueChange = { newDuration -> duration = newDuration }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            CodeExample(animationType = animationType)
                        }
                    }
                }
            }
        }
    }
}