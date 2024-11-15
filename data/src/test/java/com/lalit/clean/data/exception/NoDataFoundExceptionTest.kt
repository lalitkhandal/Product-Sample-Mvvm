package com.lalit.clean.data.exception

import org.junit.Assert.assertEquals
import org.junit.Test

class NoDataFoundExceptionTest {
    @Test
    fun `test exception message`() {
        try {
            // Throwing the exception
            throw NoDataFoundException()
        } catch (e: NoDataFoundException) {
            // Verifying that the exception is of the expected type
            assertEquals("Data Not Available", e.message)
        }
    }
}