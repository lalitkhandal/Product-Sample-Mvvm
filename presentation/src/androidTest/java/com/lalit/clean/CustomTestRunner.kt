package com.lalit.clean

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
import kotlin.jvm.java

/**
 * A custom runner to set up the instrumented application class for tests.
 * This class is used in the build.gradle.kts for the presentation
 */
//
@Suppress("unused")
class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
