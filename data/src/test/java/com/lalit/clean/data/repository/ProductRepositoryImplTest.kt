package com.lalit.clean.data.repository

import com.lalit.clean.data.datasource.ProductDataSource
import com.lalit.clean.data.mockProductEntityList
import com.lalit.clean.domain.util.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.Exception

/**
 * Unit test class for [ProductRepositoryImpl], responsible for testing the repository methods
 * that combine remote and local data sources for product-related operations.
 */
class ProductRepositoryImplTest {
    private var remoteDataSource: ProductDataSource.Remote = mockk()
    private var localDataSource: ProductDataSource.Local = mockk()

    lateinit var productRepositoryImpl: ProductRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        productRepositoryImpl = ProductRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `test when getProducts is called and api return success if local data not available`() =
        runTest {
            coEvery { localDataSource.getProducts() } returns Result.Error(Exception("Error"))
            coJustRun { localDataSource.saveProducts(any()) }
            coJustRun { localDataSource.clearProducts() }
            coEvery { remoteDataSource.getProducts() } returns Result.Success(mockProductEntityList)

            val result = productRepositoryImpl.getProducts(true)

            assertThat(result, instanceOf(Result.Success::class.java))
            assertTrue(result is Result.Success)
            assertEquals(mockProductEntityList, (result as Result.Success).data)
        }

    @Test
    fun `test when getProducts is called and local data available`() =
        runTest {
            coEvery { localDataSource.getProducts() } returns Result.Success(mockProductEntityList)

            val result = productRepositoryImpl.getProducts(false)

            assertThat(result, instanceOf(Result.Success::class.java))
            assertTrue(result is Result.Success)
            assertEquals(mockProductEntityList, (result as Result.Success).data)
        }

    @Test
    fun `test when getProducts is called and api return Error`() = runTest {
        coEvery { localDataSource.getProducts() } returns Result.Error(Exception("Error"))
        coEvery { remoteDataSource.getProducts() } returns Result.Error(Exception("Error"))

        val result = productRepositoryImpl.getProducts(false)

        assertThat(result, instanceOf(Result.Error::class.java))
    }
}