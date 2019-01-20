package com.hellmund.shopifychallenge.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body_html") val description: String,
    @SerializedName("vendor") val vendor: String,
    @SerializedName("product_type") val productType: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("variants") val variants: List<ProductVariant>,
    @SerializedName("images") val images: List<Image>
)
