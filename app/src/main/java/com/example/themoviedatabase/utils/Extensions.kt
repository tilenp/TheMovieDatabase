package com.example.themoviedatabase.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.throttleFirst(
    coroutineScope: CoroutineScope,
    periodMillis: Long
): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var throttleJob: Job? = null
        collect { value ->
            if (throttleJob?.isCompleted != false) {
                emit(value)
                throttleJob = coroutineScope.launch {
                    delay(periodMillis)
                }
            }
        }
    }
}