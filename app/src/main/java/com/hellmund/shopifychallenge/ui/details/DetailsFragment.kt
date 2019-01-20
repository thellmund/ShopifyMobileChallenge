package com.hellmund.shopifychallenge.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.data.model.Collection
import com.hellmund.shopifychallenge.di.ViewModelFactory
import com.hellmund.shopifychallenge.di.injector
import com.hellmund.shopifychallenge.ui.details.di.DetailsModule
import com.hellmund.shopifychallenge.ui.shared.BaseFragment
import com.hellmund.shopifychallenge.util.observe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.layout_collection_details.*
import javax.inject.Inject
import javax.inject.Provider

class DetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProvider: Provider<DetailsViewModel>

    private val viewModel: DetailsViewModel by lazy {
        val factory = ViewModelFactory(viewModelProvider)
        ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
    }

    private val errorSnackbar: Snackbar by lazy {
        val view = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        Snackbar.make(view, R.string.error_message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) { viewModel.refresh() }
            .setActionTextColor(color)
    }

    private val collection: Collection by lazy {
        arguments?.getParcelable<Collection>(COLLECTION_INTENT_EXTRA)
            ?: throw IllegalStateException("No Collection passed to DetailsFragment")
    }

    private val adapter: ProductsAdapter by lazy { ProductsAdapter(collection) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.detailsComponent()
            .detailsModule(DetailsModule())
            .collectionId(collection.id)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle()
        setupRecyclerView()
        populateSummaryCard()
        viewModel.viewState.observe(viewLifecycleOwner, this::render)
    }

    private fun setToolbarTitle() {
        requireActivity().title = collection.title
    }

    private fun setupRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
        swipeRefreshLayout.setColorSchemeResources(
            R.color.lightColorAccent,
            R.color.darkColorAccent
        )

        productsRecyclerView.setHasFixedSize(true)
        productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        productsRecyclerView.itemAnimator = DefaultItemAnimator()
        productsRecyclerView.adapter = adapter
        productsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
    }

    private fun populateSummaryCard() {
        Picasso.get()
            .load(collection.image.url)
            .into(imageView)

        titleTextView.text = collection.title

        if (collection.description.isNotBlank()) {
            descriptionTextView.text = collection.description
        } else {
            descriptionTextView.text = getString(R.string.no_description_available)
        }
    }

    private fun render(viewState: DetailsViewState) {
        swipeRefreshLayout.isRefreshing = viewState.isLoading
        productsRecyclerView.isVisible = viewState.products.isNotEmpty()
        productsPlaceholderLayout.isVisible = viewState.products.isEmpty()
        adapter.update(viewState.products)

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

        private const val COLLECTION_INTENT_EXTRA = "COLLECTION_INTENT_EXTRA"

        fun newInstance(collection: Collection): DetailsFragment {
            return DetailsFragment().apply {
                arguments = bundleOf(COLLECTION_INTENT_EXTRA to collection)
            }
        }

    }

}
