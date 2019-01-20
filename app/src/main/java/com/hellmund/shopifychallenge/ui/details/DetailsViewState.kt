package com.hellmund.shopifychallenge.ui.details

import com.hellmund.shopifychallenge.ui.details.viewmodel.ProductViewEntity

data class DetailsViewState(
    val products: List<ProductViewEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) {

    fun toData(collections: List<ProductViewEntity>? = null): DetailsViewState {
        return copy(products = collections ?: this.products, isLoading = false, isError = false)
    }

    fun toLoading(): DetailsViewState {
        return copy(isLoading = true, isError = false)
    }

    fun toError(): DetailsViewState {
        return copy(isLoading = false, isError = true)
    }

    companion object {

        fun initial() = DetailsViewState(isLoading = true)

    }

}
