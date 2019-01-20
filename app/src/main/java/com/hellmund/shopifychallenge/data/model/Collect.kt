package com.hellmund.shopifychallenge.data.model

import com.google.gson.annotations.SerializedName

data class Collect(
    @SerializedName("product_id") val productId: Long
)