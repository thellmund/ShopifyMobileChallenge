package com.hellmund.shopifychallenge.ui.details.viewmodel

import com.hellmund.shopifychallenge.data.model.Image

data class ProductViewEntity(
    val id: Long,
    val title: String,
    val description: String,
    val vendor: String,
    val productType: String,
    val tags: String,
    val formattedVariants: String,
    val images: List<Image>
)
