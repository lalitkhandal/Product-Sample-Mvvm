package com.lalit.clean.domain.util

import com.lalit.clean.domain.util.Result.Success

/**
 * A sealed class representing the result of an operation.
 *
 * This class is used to encapsulate the outcome of a computation or network call.
 * It can either represent a successful result with associated data or an error with an exception.
 *
 * @param T The type of data contained in the result
 */
sealed class Result<T> {
    /**
     * Represents a successful operation.
     *
     * This class holds the successful result of an operation, encapsulated in the `data` property.
     *
     * @param T The type of data returned on success.
     * @property data The actual data returned from the successful operation.
     */
    data class Success<T>(val data: T) : Result<T>()

    /**
     * Represents an error or failure.
     *
     * This class holds the exception that caused the failure, encapsulated in the `error` property.
     *
     * @param T The type of data that was expected, but is unavailable due to the error.
     * @property error The exception that caused the failure.
     */
    data class Error<T>(val error: Exception) : Result<T>()
} // End of class


/**
 * An extension function on [Result] that allows executing a given [block] when the result is successful.
 *
 * @param block The block of code to execute if the result is a [Success].
 * @return The original [Result] object, either [Success] or [Failure].
 */
inline fun <T> Result<T>.onSuccess(
    block: (T) -> Unit
): Result<T> = if (this is Success) also { block(data) } else this

/**
 * An extension function on [Result] that allows executing a given [block] when the result is a failure.
 *
 * @param block The block of code to execute if the result is a [Result.Error].
 * @return The original [Result] object, either [Result.Success] or [Result.Error].
 */
inline fun <T> Result<T>.onError(
    block: (Exception) -> Unit
): Result<T> = if (this is Result.Error) also { block(error) } else this
