package com.hellmund.shopifychallenge.data.api

import com.google.gson.annotations.SerializedName
import com.hellmund.shopifychallenge.data.model.Collection

data class CollectionsResponse(
    @SerializedName("custom_collections") val collections: List<Collection>
)
