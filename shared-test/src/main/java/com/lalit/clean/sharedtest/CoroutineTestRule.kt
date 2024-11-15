package com.lalit.clean.sharedtest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit Rule that allows controlling the main dispatcher for coroutine-based tests.
 *
 * @param testDispatcher A [TestDispatcher] to replace the main dispatcher during tests.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(private val testDispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    /**
     * Sets the [testDispatcher] as the main dispatcher before the test starts.
     *
     * This method is called automatically by JUnit before the test begins, ensuring that
     * the main dispatcher is replaced with the [testDispatcher] for the duration of the test.
     */
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Resets the main dispatcher to the original dispatcher after the test finishes.
     *
     * This method is automatically called by JUnit after the test is complete, restoring
     * the original main dispatcher to avoid affecting other tests.
     */
    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
