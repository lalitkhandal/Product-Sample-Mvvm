package com.lalit.clean.data.datasource

import com.lalit.clean.data.api.ProductApi
import com.lalit.clean.data.mockProductData
import com.lalit.clean.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit test class for [ProductRemoteDataSourceImpl]
 */
class ProductRemoteDataSourceImplTest {
    private val productApi: ProductApi = mockk()
    lateinit var productRemoteDataSourceImpl: ProductRemoteDataSourceImpl

    @Before
    fun setUp() {
        productRemoteDataSourceImpl = ProductRemoteDataSourceImpl(productApi)
    }

    @Test
    fun `test getProducts returns success when API call is successful`() = runTest {
        coEvery { productApi.getProducts() } returns mockProductData
        val result = productRemoteDataSourceImpl.getProducts()

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals(1, result.data[0].id)
    }

    @Test
    fun `test getProducts returns error when API call is failed`() = runTest {
        coEvery { productApi.getProducts() } throws Exception("Error")
        val result = productRemoteDataSourceImpl.getProducts()

        assertTrue(result is Result.Error)
        assertEquals((result as Result.Error).error.message, "Error")
    }
}