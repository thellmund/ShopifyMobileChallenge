package com.hellmund.shopifychallenge.ui.details.di

import android.content.Context
import com.hellmund.shopifychallenge.data.api.ShopifyApiService
import com.hellmund.shopifychallenge.ui.details.ProductsRemoteRepository
import com.hellmund.shopifychallenge.ui.details.viewmodel.ProductsViewEntityMapper
import com.hellmund.shopifychallenge.ui.details.viewmodel.RealVariantsFormatter
import com.hellmund.shopifychallenge.ui.details.viewmodel.VariantsFormatter
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {

    @Provides
    fun provideProductsRemoteRepository(
        apiService: ShopifyApiService
    ): ProductsRemoteRepository = ProductsRemoteRepository(apiService)

    @Provides
    fun provideVariantsFormatter(
        context: Context
    ): VariantsFormatter = RealVariantsFormatter(context)

    @Provides
    fun provideProductsViewEntityMapper(
        variantsFormatter: VariantsFormatter
    ): ProductsViewEntityMapper = ProductsViewEntityMapper(variantsFormatter)

}
