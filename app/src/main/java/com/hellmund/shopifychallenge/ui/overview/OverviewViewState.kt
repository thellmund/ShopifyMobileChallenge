package com.hellmund.shopifychallenge.ui.overview

import com.hellmund.shopifychallenge.data.model.Collection

data class OverviewViewState(
    val collections: List<Collection> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) {

    fun toData(collections: List<Collection>? = null): OverviewViewState {
        return copy(collections = collections ?: this.collections, isLoading = false, isError = false)
    }

    fun toLoading(): OverviewViewState {
        return copy(isLoading = true, isError = false)
    }

    fun toError(): OverviewViewState {
        return copy(isLoading = false, isError = true)
    }

    companion object {

        fun initial() = OverviewViewState(isLoading = true)

    }

}

