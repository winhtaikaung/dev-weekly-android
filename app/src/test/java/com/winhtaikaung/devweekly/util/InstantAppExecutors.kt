package com.winhtaikaung.devweekly.util

import com.winhtaikaung.devweekly.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}