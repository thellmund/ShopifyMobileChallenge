package com.hellmund.shopifychallenge.data.api

import com.google.gson.annotations.SerializedName
import com.hellmund.shopifychallenge.data.model.Product

data class ProductsResponse(
    @SerializedName("products") val products: List<Product>
)
