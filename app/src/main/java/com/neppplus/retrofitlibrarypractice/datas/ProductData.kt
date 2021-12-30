package com.neppplus.retrofitlibrarypractice.datas

import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

class ProductData(
    var id: Int,
    var name: String,
    var store: StoreData,
    @SerializedName("small_category")
    var smallCategory: SmallCategoryData,
    var price: Int,
    @SerializedName("image_url")
    var imageURL: String,
) {
    fun getFormattedPrice(): String {
        return "${NumberFormat.getInstance(Locale.KOREA).format(this.price)} 원"
    }
}