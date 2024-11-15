package com.lalit.clean.ui.feed

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.ProductUseCase
import com.lalit.clean.domain.util.Result
import com.lalit.clean.ui.feed.state.ResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val productUseCase: ProductUseCase) : ViewModel() {
    private val _resultUiState: MutableStateFlow<ResultUiState> =
        MutableStateFlow(ResultUiState.Loading)

    val resultUiState = _resultUiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = ResultUiState.Loading
    )

    fun fetchProducts(isForceRefresh: Boolean = false) {
        if (isForceRefresh) {
            initProductState(ResultUiState.Loading)
        }
        viewModelScope.launch {
            val response = productUseCase.getProducts(true)
            handleProductResponse(response)
        }
    }

    private fun handleProductResponse(response: Result<List<ProductEntity>>) {
        when (response) {
            is Result.Success -> initProductState(ResultUiState.Success(response.data))
            is Result.Error -> initProductState(ResultUiState.Error(response.error))
        }
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun initProductState(state: ResultUiState) {
        _resultUiState.update { state }
    }
}