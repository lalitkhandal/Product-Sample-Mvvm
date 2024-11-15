package com.lalit.clean.ui.feed.state

/**
 * Represents the state of the product data, used to handle different UI states
 * in response to loading, success, or error conditions.
 */
sealed class ResultUiState {
    /**
     * Represents the loading state while fetching data.
     */
    object Loading : ResultUiState()

    /**
     * Represents a successful response with data.
     */
    data class Success(val data: Any) : ResultUiState()

    /**
     * Represents an error state when fetching data fails.
     *
     * @param exception The exception that caused the failure.
     */
    data class Error(val exception: Exception) : ResultUiState()
} // End of class

