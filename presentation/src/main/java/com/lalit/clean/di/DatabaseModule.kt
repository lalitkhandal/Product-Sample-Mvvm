package com.lalit.clean.di

import android.content.Context
import androidx.room.Room
import com.lalit.clean.data.db.AppDatabase
import com.lalit.clean.data.db.dao.CartDao
import com.lalit.clean.data.db.dao.ProductDao
import com.lalit.clean.data.util.DiskExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module to provide database-related dependencies.
 *
 * This module is responsible for providing the instance of [AppDatabase] and its DAOs.
 * It's installed in the [SingletonComponent] to ensure the database and its DAOs are available
 * throughout the application lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    /**
     * Provides the instance of [AppDatabase].
     *
     * @param context The [Context] used to build the database.
     * @return An instance of [AppDatabase] for interacting with the product and cart tables.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        diskExecutor: DiskExecutor
    ): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "sample.db")
            .setQueryExecutor(diskExecutor)
            .setTransactionExecutor(diskExecutor)
            .build()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }
}