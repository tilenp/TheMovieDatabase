package com.example.themoviedatabase.utils

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class ExtensionsTest {

    @Test
    fun throttle_first_test() = runTest {
        val throttleInterval = 1000L
        val sharedFlow = MutableSharedFlow<Int>()
        val dispatcher = FakeDispatcherProvider()

        val job = launch(start = CoroutineStart.LAZY) {
            sharedFlow.emit(1)
            sharedFlow.emit(2)
        }

        // assert
        sharedFlow.throttleFirst(dispatcher.scope.plus(dispatcher.testDispatcher), throttleInterval).test {
            job.start()
            assertEquals(1, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun emit_after_interval_passed_test() = runTest {
        val throttleInterval = 1000L
        val sharedFlow = MutableSharedFlow<Int>()
        val dispatcher = FakeDispatcherProvider()

        val job = launch(start = CoroutineStart.LAZY) {
            sharedFlow.emit(1)
            dispatcher.scope.testScheduler.advanceTimeBy(throttleInterval + 1)
            sharedFlow.emit(2)
        }

        // assert
        sharedFlow.throttleFirst(dispatcher.scope.plus(dispatcher.testDispatcher), throttleInterval).test {
            job.start()
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            expectNoEvents()
        }
    }
}