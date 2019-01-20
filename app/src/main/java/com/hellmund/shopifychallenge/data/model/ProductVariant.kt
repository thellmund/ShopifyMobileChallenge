package com.hellmund.shopifychallenge.data.model

import com.google.gson.annotations.SerializedName

data class ProductVariant(
    @SerializedName("id") val id: Long
    // For now, we don't display anything else about a product variant. However, this can of course
    // be easily extended in the future.
)
