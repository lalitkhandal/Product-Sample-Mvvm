package com.lalit.clean.ui.feeddetails

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.CartUseCase
import com.lalit.clean.domain.usecase.DetailProductUseCase
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
class FeedDetailsViewModel @Inject constructor(
    private val detailProductUseCase: DetailProductUseCase,
    private val cartUseCase: CartUseCase,
    private val productDetailsBundle: ProductDetailsBundle
) : ViewModel() {

    private val _resultUiState: MutableStateFlow<ResultUiState> =
        MutableStateFlow(ResultUiState.Loading)

    val resultUiState = _resultUiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = ResultUiState.Loading
    )

    fun fetchProduct() {
        initProductState(ResultUiState.Loading)
        viewModelScope.launch {
            val response = detailProductUseCase.invoke(productId = productDetailsBundle.productId)
            handleProductResponse(response)
        }
    }

    private fun handleProductResponse(response: Result<ProductEntity>) {
        when (response) {
            is Result.Success -> initProductState(ResultUiState.Success(response.data))
            is Result.Error -> initProductState(ResultUiState.Error(response.error))
        }
    }

    fun saveCartProduct(product: ProductEntity, onResponse: () -> Unit) {
        viewModelScope.launch {
            cartUseCase.saveCartProduct(product)
            onResponse()
        }
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun initProductState(state: ResultUiState) {
        _resultUiState.update { state }
    }
}