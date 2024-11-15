package com.lalit.clean.di

import com.lalit.clean.data.api.ProductApi
import com.lalit.clean.data.datasource.ProductCartDataSource
import com.lalit.clean.data.datasource.ProductCartDataSourceImpl
import com.lalit.clean.data.datasource.ProductDataSource
import com.lalit.clean.data.datasource.ProductLocalDataSourceImpl
import com.lalit.clean.data.datasource.ProductRemoteDataSourceImpl
import com.lalit.clean.data.db.dao.CartDao
import com.lalit.clean.data.db.dao.ProductDao
import com.lalit.clean.data.repository.ProductCartRepositoryImpl
import com.lalit.clean.data.repository.ProductRepositoryImpl
import com.lalit.clean.domain.repository.ProductCartRepository
import com.lalit.clean.domain.repository.ProductRepository
import com.lalit.clean.domain.usecase.CartUseCase
import com.lalit.clean.domain.usecase.DetailProductUseCase
import com.lalit.clean.domain.usecase.ProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger module that provides dependencies for the data layer.
 *
 * This module is used to define and provide dependencies that are scoped to the
 * SingletonComponent, meaning the dependencies will live for the duration of the app's lifetime.
 *
 * @see SingletonComponent for more details about the scope of this module.
 */

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        sourceRemote: ProductDataSource.Remote,
        sourceLocal: ProductDataSource.Local,
    ): ProductRepository {
        return ProductRepositoryImpl(sourceRemote, sourceLocal)
    } // End of function

    @Provides
    @Singleton
    fun provideProductRemoteDataSourceImpl(productApi: ProductApi): ProductDataSource.Remote {
        return ProductRemoteDataSourceImpl(productApi)
    } // End of function

    @Provides
    @Singleton
    fun provideProductLocalDataSourceImpl(productDao: ProductDao): ProductDataSource.Local {
        return ProductLocalDataSourceImpl(productDao)
    } // End of function

    @Provides
    fun provideProductUseCase(productRepository: ProductRepository): ProductUseCase {
        return ProductUseCase(productRepository)
    } // End of function

    @Provides
    fun provideDetailProductUseCase(productRepository: ProductRepository): DetailProductUseCase {
        return DetailProductUseCase(productRepository)
    } // End of function

    @Provides
    @Singleton
    fun provideProductCartRepository(
        productCartDataSource: ProductCartDataSource
    ): ProductCartRepository {
        return ProductCartRepositoryImpl(productCartDataSource)
    } // End of function

    @Provides
    @Singleton
    fun provideProductCartDataSourceImpl(cartDao: CartDao): ProductCartDataSource {
        return ProductCartDataSourceImpl(cartDao)
    } // End of function

    @Provides
    fun provideCartUseCase(productCartRepository: ProductCartRepository): CartUseCase {
        return CartUseCase(productCartRepository)
    } // End of function

} // End of class