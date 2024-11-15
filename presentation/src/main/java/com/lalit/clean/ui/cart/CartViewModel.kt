package com.lalit.clean.ui.cart

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalit.clean.domain.entities.ProductEntity
import com.lalit.clean.domain.usecase.CartUseCase
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
class CartViewModel @Inject constructor(private val cartUseCase: CartUseCase) : ViewModel() {
    private val _cartState: MutableStateFlow<ResultUiState> =
        MutableStateFlow(ResultUiState.Loading)

    val cartState = _cartState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = ResultUiState.Loading
    )

    fun fetchCartData() {
        viewModelScope.launch {
            val response = cartUseCase.getCartProducts()
            handleCartResponse(response)
        }
    }

    private fun handleCartResponse(response: Result<List<ProductEntity>>) {
        when (response) {
            is Result.Success -> initCartState(ResultUiState.Success(response.data))
            is Result.Error -> initCartState(ResultUiState.Error(response.error))
        }
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun initCartState(state: ResultUiState) {
        _cartState.update { state }
    }
}