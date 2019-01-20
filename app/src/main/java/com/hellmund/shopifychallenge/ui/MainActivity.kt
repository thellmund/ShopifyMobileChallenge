package com.hellmund.shopifychallenge.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.ui.shared.BaseFragment
import com.hellmund.shopifychallenge.util.navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hellmund.shopifychallenge.R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.openOverview()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as? BaseFragment
            fragment?.onBackPressed()
        }
    }

}
