package com.vanlee.sportify.scheduler

interface Scheduler {
    fun execute(runnable: Runnable)
}