package com.lalit.clean.data.exception

/**
 * A custom exception class used to indicate that no data was found during data retrieval operations.
 *
 *
 * @constructor Creates a NoDataFoundException with an optional message.
 * @param message A custom message that describes the cause of the exception. Defaults to "Data Not Available".
 */
class NoDataFoundException(message: String = "Data Not Available") : Exception(message)