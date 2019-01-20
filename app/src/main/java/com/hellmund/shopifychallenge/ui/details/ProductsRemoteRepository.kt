package com.hellmund.shopifychallenge.ui.details

import com.hellmund.shopifychallenge.data.api.ShopifyApiService
import com.hellmund.shopifychallenge.data.model.Product
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsRemoteRepository @Inject constructor(
    private val apiService: ShopifyApiService
) {

    fun fetchProducts(collectionId: Long): Observable<List<Product>> {
        return apiService.fetchCollects(collectionId)
            .subscribeOn(Schedulers.io())
            .map { response -> response.collects.map { it.productId }.joinToString(",") }
            .flatMap { apiService.fetchProducts(it) }
            .map { it.products }
    }

}
