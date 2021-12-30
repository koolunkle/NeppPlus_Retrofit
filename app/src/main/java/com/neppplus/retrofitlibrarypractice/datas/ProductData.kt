package com.neppplus.retrofitlibrarypractice.datas

import com.google.gson.annotations.SerializedName

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
}