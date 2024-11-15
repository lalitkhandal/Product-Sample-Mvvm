package com.lalit.clean.data.datasource

import com.lalit.clean.data.db.dao.CartDao
import com.lalit.clean.data.db.entities.CartDbEntity
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

class ProductCartDataSourceImplTest {
    private val cartDao: CartDao = mockk()
    private lateinit var productCartDataSourceImpl: ProductCartDataSourceImpl

    @Before
    fun setup() {
        productCartDataSourceImpl = ProductCartDataSourceImpl(cartDao)
    }

    @Test
    fun `test getCartProducts is called with success when database has data`() = runTest {
        val cartDbEntity = getCartDbEntity(1, 10)
        val cartDbEntity1 = getCartDbEntity(2, 11)
        coEvery { cartDao.getCartProducts() } returns listOf(cartDbEntity, cartDbEntity1)
        val result = productCartDataSourceImpl.getCartProducts()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data.size)
        assertEquals(cartDbEntity.toDomain(), result.data[0])
        assertEquals(cartDbEntity1.toDomain(), result.data[1])
        assertEquals(10, result.data[0].id)
        assertEquals(11, result.data[1].id)
    }

    @Test
    fun `test getCartProducts is called with error when data is not available`() = runTest {
        coEvery { cartDao.getCartProducts() } returns listOf()
        val result = productCartDataSourceImpl.getCartProducts()

        assertTrue(result is Result.Error)
        assertEquals(
            (result as Result.Error).error.message,
            "Cart is Empty, Please go to order details page to add product in the cart."
        )
    }

    @Test
    fun `test saveCartProduct calls saveCartProduct on cartDao`() = runTest {
        val cartDbEntity = getCartDbEntity(1, 10).toDomain()

        coJustRun { cartDao.saveCartProduct(any()) }
        coEvery { cartDao.getCartProduct(any()) } returns null

        productCartDataSourceImpl.saveCartProduct(cartDbEntity)

        coVerify { cartDao.getCartProduct(any()) }
        coVerify { cartDao.saveCartProduct(any()) }
    }

    @Test
    fun `test saveCartProduct calls updateCartProduct on cartDao`() = runTest {
        val cartDbEntity = getCartDbEntity(1, 10)
        coJustRun { cartDao.saveCartProduct(any()) }
        coEvery { cartDao.getCartProduct(any()) } returns cartDbEntity
        coJustRun { cartDao.updateCartProduct(any(), any()) }

        productCartDataSourceImpl.saveCartProduct(cartDbEntity.toDomain())
        coVerify { cartDao.getCartProduct(any()) }
        coVerify { cartDao.updateCartProduct(any(), any()) }
    }

    @Test
    fun `test removeCartProduct calls removeCartProduct on cartDao`() = runTest {
        coJustRun { cartDao.removeCartProduct(any()) }
        productCartDataSourceImpl.removeCartProduct(1)
        coVerify { cartDao.removeCartProduct(any()) }
    }

    private fun getCartDbEntity(id: Long, productId: Int): CartDbEntity = CartDbEntity(
        id = id,
        productId = productId,
        category = "tesCategory",
        description = "testDescription",
        thumbnail = "testThumbnail",
        price = 12.0,
        title = "testTitle",
        quantity = 1
    )
}