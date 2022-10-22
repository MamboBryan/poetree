package com.mambo.poetree.utils

import kotlinx.coroutines.*

class Timer {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(
        delay: Long = 0,
        repeat: Long = 0,
        action: () -> Unit
    ) = scope.launch(Dispatchers.IO) {
        delay(delay)
        if (repeat > 0) {
            while (true) {
                action()
                delay(repeat)
            }
        } else {
            action()
        }
    }

}