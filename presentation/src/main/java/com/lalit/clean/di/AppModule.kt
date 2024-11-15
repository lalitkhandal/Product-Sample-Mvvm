package com.lalit.clean.di

import com.lalit.clean.data.util.DiskExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger module to provide dependencies for the application.
 *
 * This module is installed in the [SingletonComponent] to provide global instances across the app.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /**
     * Provides a singleton instance of [DiskExecutor].
     *
     * @return A [DiskExecutor] instance for performing tasks on a single background thread.
     */
    @Provides
    fun provideDiskExecutor(): DiskExecutor {
        return DiskExecutor()
    }
}