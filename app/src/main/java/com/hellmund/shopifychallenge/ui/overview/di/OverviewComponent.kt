package com.hellmund.shopifychallenge.ui.overview.di

import com.hellmund.shopifychallenge.ui.overview.OverviewFragment
import dagger.Subcomponent

@Subcomponent(modules = [OverviewModule::class])
interface OverviewComponent {

    fun inject(overviewFragment: OverviewFragment)

    @Subcomponent.Builder
    interface Builder {

        fun overviewModule(overviewModule: OverviewModule): Builder

        fun build(): OverviewComponent

    }

}
