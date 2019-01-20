package com.hellmund.shopifychallenge.ui.details.di

import com.hellmund.shopifychallenge.ui.details.DetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {

    fun inject(detailsFragment: DetailsFragment)

    @Subcomponent.Builder
    interface Builder {

        fun detailsModule(detailsModule: DetailsModule): Builder

        @BindsInstance
        fun collectionId(@CollectionId collectionId: Long): Builder

        fun build(): DetailsComponent

    }

}
