package com.hellmund.shopifychallenge.ui.overview.di

import com.hellmund.shopifychallenge.data.api.ShopifyApiService
import com.hellmund.shopifychallenge.ui.overview.CollectionsRemoteRepository
import dagger.Module
import dagger.Provides

@Module
class OverviewModule {

    @Provides
    fun provideCollectionsRemoteRepository(
        apiService: ShopifyApiService
    ): CollectionsRemoteRepository = CollectionsRemoteRepository(apiService)

}
