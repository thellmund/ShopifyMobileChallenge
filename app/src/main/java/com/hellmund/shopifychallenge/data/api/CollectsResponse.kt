package com.hellmund.shopifychallenge.data.api

import com.google.gson.annotations.SerializedName
import com.hellmund.shopifychallenge.data.model.Collect

data class CollectsResponse(
    @SerializedName("collects") val collects: List<Collect>
)
