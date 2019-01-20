package com.hellmund.shopifychallenge.ui.overview

import com.hellmund.shopifychallenge.data.api.ShopifyApiService
import com.hellmund.shopifychallenge.data.model.Collection
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CollectionsRemoteRepository @Inject constructor(
    private val apiService: ShopifyApiService
) {

    fun fetchCollections(): Observable<List<Collection>> {
        return apiService
            .fetchCollections()
            .subscribeOn(Schedulers.io())
            .map { it.collections }
    }

}
