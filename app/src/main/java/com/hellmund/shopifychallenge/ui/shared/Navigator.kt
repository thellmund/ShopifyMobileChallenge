package com.hellmund.shopifychallenge.ui.shared

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.data.model.Collection
import com.hellmund.shopifychallenge.ui.details.DetailsFragment
import com.hellmund.shopifychallenge.ui.overview.OverviewFragment

class Navigator(private val activity: AppCompatActivity) {

    fun openOverview() {
        activity.supportFragmentManager.transaction {
            replace(R.id.contentFrame, OverviewFragment.newInstance())
        }
    }

    fun openCollectionDetails(collection: Collection) {
        activity.supportFragmentManager.transaction {
            setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            replace(R.id.contentFrame, DetailsFragment.newInstance(collection))
            addToBackStack(null)
        }
    }

}
