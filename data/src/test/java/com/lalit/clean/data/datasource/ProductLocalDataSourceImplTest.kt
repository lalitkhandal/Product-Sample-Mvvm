package com.lalit.clean.data.datasource

import com.lalit.clean.data.db.dao.ProductDao
import com.lalit.clean.data.db.entities.ProductDbEntity
import com.lalit.clean.data.exception.NoDataFoundException
import com.lalit.clean.data.mapper.toDomain
import com.lalit.clean.domain.util.Result
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit test class for [ProductLocalDataSourceImpl]
 */
class ProductLocalDataSourceImplTest {
    private val productDao: ProductDao = mockk()
    lateinit var productLocalDataSourceImpl: ProductLocalDataSourceImpl

    @Before
    fun setUp() {
        productLocalDataSourceImpl = ProductLocalDataSourceImpl(productDao)
    }

    @Test
    fun `test getProducts returns success when products exist in database`() = runTest {
        val productDbEntity = getProductDbEntity(1)
        val productDbEntity1 = getProductDbEntity(2)
        coEvery { productDao.getProducts() } returns listOf(productDbEntity, productDbEntity1)
        val result = productLocalDataSourceImpl.getProducts()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data.size)
        assertEquals(productDbEntity.toDomain(), result.data[0])
        assertEquals(productDbEntity1.toDomain(), result.data[1])
        assertEquals(1, result.data[0].id)
        assertEquals(2, result.data[1].id)
    }

    @Test
    fun `test getProducts returns error when products not exist in database`() = runTest {
        coEvery { productDao.getProducts() } returns listOf()
        val result = productLocalDataSourceImpl.getProducts()
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is NoDataFoundException)
        assertEquals(result.error.message, "Data Not Available")
    }

    @Test
    fun `test getProduct returns success when products exist in database`() = runTest {
        val productDbEntity = getProductDbEntity(1)
        coEvery { productDao.getProduct(1) } returns productDbEntity
        val result = productLocalDataSourceImpl.getProduct(1)

        assertTrue(result is Result.Success)
        assertEquals(productDbEntity.toDomain(), (result as Result.Success).data)
        assertEquals(1, result.data.id)
    }

    @Test
    fun `test getProduct returns error when product not exist in database`() = runTest {
        coEvery { productDao.getProduct(any()) } returns null
        val result = productLocalDataSourceImpl.getProduct(1)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is NoDataFoundException)
        assertEquals(result.error.message, "Data Not Available")
    }

    @Test
    fun `test saveProducts calls saveProducts on productDao`() = runTest {
        val productEntity = getProductDbEntity(1).toDomain()
        coJustRun { productDao.saveProducts(any()) }
        productLocalDataSourceImpl.saveProducts(listOf(productEntity))
        coVerify { productDao.saveProducts(any()) }
    }

    @Test
    fun `test clearProducts calls clearAll on productDao`() = runTest {
        coJustRun { productDao.clearAll() }
        productLocalDataSourceImpl.clearProducts()
        coVerify { productDao.clearAll() }
    }


    private fun getProductDbEntity(id: Int): ProductDbEntity = ProductDbEntity(
        id = id,
        category = "tesCategory",
        description = "testDescription",
        thumbnail = "testThumbnail",
        price = 12.0,
        title = "testTitle"
    )
}