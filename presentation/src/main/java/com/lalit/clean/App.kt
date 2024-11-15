package com.lalit.clean

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main application class for the app, responsible for initializing global components and configurations.
 *
 * This class is used to set up the application-level dependencies, configuration, and any necessary
 * components when the app is created. It extends [Application] and is specified in the AndroidManifest.xml
 * as the `android:name` attribute for the application tag.
 *
 */
@HiltAndroidApp
class App : Application()