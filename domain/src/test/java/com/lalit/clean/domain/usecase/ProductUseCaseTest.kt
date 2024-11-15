package com.lalit.clean.domain.usecase

import com.lalit.clean.domain.repository.ProductRepository
import com.lalit.clean.domain.util.Result
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Unit test class for [ProductUseCase], responsible for testing the core business logic
 * of fetching and handling product data using the repository.
 */
class ProductUseCaseTest {

    @MockK
    val repository: ProductRepository = mockk()

    private lateinit var productUseCase: ProductUseCase

    @Before
    fun setup() {
        productUseCase = ProductUseCase(repository)
    }

    @Test
    fun `When network request is successful then getAllProducts returns success`() =
        runTest {
            coEvery { repository.getProducts(true) } returns Result.Success(listOf())

            val result = productUseCase.getProducts(true)

            assertThat(result, instanceOf(Result.Success::class.java))
        }

    @Test
    fun `Given valid params when network request fails then getCreditPartnerInformation return failure`() =
        runTest {
            coEvery { repository.getProducts(true) } returns Result.Error(Exception())

            val result = productUseCase.getProducts(true)

            assertThat(result, instanceOf(Result.Error::class.java))
        }
}