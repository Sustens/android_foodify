package com.sustens.foodify.model

class ItemsResponse : ArrayList<ItemsResponseItem>()


data class ItemsResponseItem(
    val CO2_rating: Double,
    val FAT: Double,
    val ID: String,
    val PROT: Double,
    val WATER: String,
    val Weight: Weight,
    val carbon_footprint_100g: Double,
    val category: String,
    val energy_100g: String,
    val main_category: String,
    val parent_category: String,
    val price_per_100g: String,
    val product_name: String,
    val store_currency: String
)

data class Weight(
    val serving: Double
)