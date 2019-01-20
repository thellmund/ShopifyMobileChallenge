package com.hellmund.shopifychallenge.di

import com.hellmund.shopifychallenge.ui.details.di.DetailsComponent
import com.hellmund.shopifychallenge.ui.overview.OverviewFragment
import com.hellmund.shopifychallenge.ui.overview.di.OverviewComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun overviewComponent(): OverviewComponent.Builder

    fun detailsComponent(): DetailsComponent.Builder

    fun inject(overviewFragment: OverviewFragment)

}
