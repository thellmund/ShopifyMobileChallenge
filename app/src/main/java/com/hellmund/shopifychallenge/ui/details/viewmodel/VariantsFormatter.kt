package com.hellmund.shopifychallenge.ui.details.viewmodel

import android.content.Context
import com.hellmund.shopifychallenge.R
import com.hellmund.shopifychallenge.data.model.ProductVariant

interface VariantsFormatter {
    fun format(variants: List<ProductVariant>): String
}

class RealVariantsFormatter(
    private val context: Context
) : VariantsFormatter {

    override fun format(variants: List<ProductVariant>): String {
        val size = variants.size
        return context.resources.getQuantityString(R.plurals.variants_format_string, size, size)
    }

}
