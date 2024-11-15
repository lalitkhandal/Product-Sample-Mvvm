package com.lalit.clean.data.api

import com.lalit.clean.data.entities.ProductData
import com.lalit.clean.data.entities.ProductDataItem
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface representing the API endpoints for managing products.
 *
 * This interface defines methods for interacting with product data used with a network library
 */
interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductData

    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Int): ProductDataItem
}