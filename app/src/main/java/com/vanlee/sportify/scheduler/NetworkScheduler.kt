package com.vanlee.sportify.scheduler

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class NetworkScheduler : Scheduler {
    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }
}