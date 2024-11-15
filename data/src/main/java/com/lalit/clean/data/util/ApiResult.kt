package com.lalit.clean.data.util

import com.lalit.clean.domain.util.Result
import java.net.UnknownHostException

/**
 * A helper function to handle the result of a suspended API call and return a [Result] object.
 *
 * This function wraps a network or API request and handles both successful and error outcomes. It simplifies error
 * handling by returning a [Result] object, which can either be a success or an error based on the outcome of the
 * [apiCall]. This is useful for processing network responses in a consistent way.
 *
 * @param apiCall A suspended function that represents the API call or task to be executed.
 * This function should return a result of type [T] when successful.
 *
 * @return A [Result] object that will be either:
 * - [Result.Success] with the result of the API call, or
 * - [Result.Error] containing the exception thrown during the execution of the API call.
 */
suspend fun <T> processRequestResult(apiCall: suspend () -> T): Result<T> = try {
    Result.Success(apiCall.invoke()) // Execute the API call and return a success result
} catch (e: Exception) {
    // Catch any exception and return it as an error result
    val error = if (e is UnknownHostException)
        Exception("Something Went Wrong, Please retry again")
    else e
    Result.Error(error)
} // End of function