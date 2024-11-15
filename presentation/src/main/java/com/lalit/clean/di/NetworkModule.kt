package com.lalit.clean.di

import com.lalit.clean.data.BuildConfig
import com.lalit.clean.data.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * A Dagger module that provides network-related dependencies.
 *
 * This module is used to configure and provide instances of [Retrofit], [OkHttpClient],
 * and [ProductApi] for the application. It is installed in the [SingletonComponent], meaning
 * the provided instances are singletons and live for the duration of the app's lifecycle.
 *
 * @see SingletonComponent for more details about the scope of this module.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides a singleton instance of [Retrofit].
     *
     * @param client The [OkHttpClient] instance used for handling network requests.
     * @return A [Retrofit] instance configured with the necessary converters and client.
     */
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    } // End of function

    /**
     * Provides a singleton instance of [OkHttpClient].
     *
     * @return A new instance of [OkHttpClient] with logging enabled.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build() // End of function

    /**
     * Provides a singleton instance of [ProductApi].
     *
     * @param retrofit The [Retrofit] instance used to create the [ProductApi].
     * @return A [ProductApi] instance for making API requests.
     */
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    } // End of function
} // End of class