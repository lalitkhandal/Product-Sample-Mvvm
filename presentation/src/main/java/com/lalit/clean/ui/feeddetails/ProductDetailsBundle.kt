package com.lalit.clean.ui.feeddetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.lalit.clean.ui.navigation.NavScreen
import javax.inject.Inject

class ProductDetailsBundle @Inject constructor(savedStateHandle: SavedStateHandle) {
    val productId: Int = savedStateHandle.toRoute<NavScreen.FeedDetails>().productId
}
