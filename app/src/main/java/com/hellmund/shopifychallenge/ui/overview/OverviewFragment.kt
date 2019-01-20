package com.hellmund.shopifychallenge.ui.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.di.ViewModelFactory
import com.hellmund.shopifychallenge.di.injector
import com.hellmund.shopifychallenge.ui.overview.di.OverviewModule
import com.hellmund.shopifychallenge.ui.shared.BaseFragment
import com.hellmund.shopifychallenge.util.observe
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject
import javax.inject.Provider

class OverviewFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProvider: Provider<OverviewViewModel>

    private val viewModel: OverviewViewModel by lazy {
        val factory = ViewModelFactory(viewModelProvider)
        ViewModelProviders.of(this, factory).get(OverviewViewModel::class.java)
    }

    private val errorSnackbar: Snackbar by lazy {
        val view = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        Snackbar.make(view, R.string.error_message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) { viewModel.refresh() }
            .setActionTextColor(color)
    }

    private val adapter = CollectionsAdapter {
        navigator.openCollectionDetails(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.overviewComponent()
            .overviewModule(OverviewModule())
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().title = getString(R.string.collections)

        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
        swipeRefreshLayout.setColorSchemeResources(
            R.color.lightColorAccent,
            R.color.darkColorAccent
        )

        collectionsRecyclerView.setHasFixedSize(true)
        collectionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        collectionsRecyclerView.itemAnimator = DefaultItemAnimator()
        collectionsRecyclerView.adapter = adapter
        collectionsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))

        viewModel.viewState.observe(viewLifecycleOwner, this::render)
    }

    private fun render(viewState: OverviewViewState) {
        swipeRefreshLayout.isRefreshing = viewState.isLoading
        collectionsRecyclerView.isVisible = viewState.collections.isNotEmpty()
        collectionsPlaceholderLayout.isVisible = viewState.collections.isEmpty()
        adapter.update(viewState.collections)

        if (viewState.isError) {
            errorSnackbar.show()
        } else {
            errorSnackbar.dismiss()
        }
    }

    override fun onDestroyView() {
        errorSnackbar.dismiss()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = OverviewFragment()
    }

}
