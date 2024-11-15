package com.lalit.clean.data.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * A simple [Executor] implementation that uses a single-threaded executor to run tasks.
 *
 * This class ensures that tasks are executed sequentially on a single background thread,
 * suitable for disk I/O or other operations that require serialization.
 */
class DiskExecutor : Executor {
    // Internal single-threaded executor
    private val executor: Executor = Executors.newSingleThreadExecutor()

    /**
     * Executes a given [Runnable] task on a single background thread.
     *
     * @param runnable The task to be executed.
     */
    override fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }
}
