package com.hellmund.shopifychallenge.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopifyApiService {

    @GET("custom_collections.json")
    fun fetchCollections(
        @Query("page") page: Int = 1
    ): Observable<CollectionsResponse>

    @GET("collects.json")
    fun fetchCollects(
        @Query("collection_id") collectionId: Long,
        @Query("page") page: Int = 1
    ): Observable<CollectsResponse>

    @GET("products.json")
    fun fetchProducts(
        @Query("ids") productIds: String,
        @Query("page") page: Int = 1
    ): Observable<ProductsResponse>

}
