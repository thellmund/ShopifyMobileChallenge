package com.hellmund.shopifychallenge.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.data.model.Collection
import com.hellmund.shopifychallenge.ui.details.viewmodel.ProductViewEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_product.view.*

class ProductsAdapter(
    private val collection: Collection
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private val items = mutableListOf<ProductViewEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], collection)
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<ProductViewEntity>) {
        val diffResult = DiffUtil.calculateDiff(ProductsDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: ProductViewEntity, collection: Collection) = with(itemView) {
            val image = product.images.firstOrNull()
            Picasso.get()
                .load(image?.url)
                .into(imageView)

            Picasso.get()
                .load(collection.image.url)
                .into(collectionImageView)

            titleTextView.text = product.title
            inventoryCountTextView.text = product.formattedVariants
            collectionTitleTextView.text = collection.title
        }

    }

    class ProductsDiffUtilCallback(
        private val oldItems: List<ProductViewEntity>,
        private val newItems: List<ProductViewEntity>
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
