package com.hellmund.shopifychallenge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    @SerializedName("id") val id: Long,
    @SerializedName("handle") val handle: String,
    @SerializedName("title") val title: String,
    @SerializedName("body_html") val description: String,
    @SerializedName("image") val image: Image
) : Parcelable
