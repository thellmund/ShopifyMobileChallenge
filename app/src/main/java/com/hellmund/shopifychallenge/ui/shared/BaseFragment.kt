package com.hellmund.shopifychallenge.ui.shared

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hellmund.shopifychallenge.util.navigator

abstract class BaseFragment : Fragment() {

    protected val navigator: Navigator by lazy {
        (requireActivity() as AppCompatActivity).navigator
    }

    override fun onStart() {
        super.onStart()
        val isHome = requireActivity().supportFragmentManager.backStackEntryCount == 0
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(isHome.not())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onBackPressed() {
        requireActivity().supportFragmentManager.popBackStack()
    }

}
