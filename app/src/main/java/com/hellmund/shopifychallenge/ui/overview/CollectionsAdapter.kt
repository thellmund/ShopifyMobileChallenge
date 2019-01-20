package com.hellmund.shopifychallenge.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.data.model.Collection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_collection.view.*

class CollectionsAdapter(
    private val onItemClick: (Collection) -> Unit
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    private val items = mutableListOf<Collection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_collection, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<Collection>) {
        val diffResult = DiffUtil.calculateDiff(CollectionsDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(collection: Collection, onItemClick: (Collection) -> Unit) = with(itemView) {
            Picasso.get()
                .load(collection.image.url)
                .into(imageView)

            titleTextView.text = collection.title
            setOnClickListener { onItemClick(collection) }
        }

    }

    class CollectionsDiffUtilCallback(
        private val oldItems: List<Collection>,
        private val newItems: List<Collection>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

    }

}
