package com.t8rin.animation.samples.ui.viewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t8rin.animation.samples.ui.components.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _navigationStack: MutableState<List<Question>> =
        mutableStateOf(listOf(Question.Start))
    val navigationStack: List<Question> by _navigationStack

    private var job: Job? = null

    fun pop() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(400)
            if (_navigationStack.value.size > 1) {
                _navigationStack.value = _navigationStack.value.dropLast(1)
            }
        }
    }

    fun popAll() {
        _navigationStack.value = listOf(Question.Start)
    }

    fun push(question: Question) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(400)
            _navigationStack.value = _navigationStack.value + question
        }
    }
}