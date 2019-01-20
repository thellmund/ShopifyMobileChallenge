package com.hellmund.shopifychallenge.ui.details.viewmodel

import com.hellmund.shopifychallenge.data.model.Product
import io.reactivex.functions.Function
import javax.inject.Inject

class ProductsViewEntityMapper @Inject constructor(
    private val variantsFormatter: VariantsFormatter
) : Function<List<Product>, List<ProductViewEntity>> {

    override fun apply(products: List<Product>): List<ProductViewEntity> {
        return products.map(this::createViewEntity)
    }

    private fun createViewEntity(p: Product): ProductViewEntity {
        val formattedVariants = variantsFormatter.format(p.variants)
        return ProductViewEntity(p.id, p.title, p.description, p.vendor,
            p.productType, p.tags, formattedVariants, p.images)
    }

}
