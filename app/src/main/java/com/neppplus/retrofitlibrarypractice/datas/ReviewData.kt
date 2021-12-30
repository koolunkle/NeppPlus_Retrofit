package com.neppplus.retrofitlibrarypractice.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ReviewData(
    var id: Int,
    var title: String,
    var content: String,
    var score: Double,
    var user: UserData,
    var product: ProductData,
    @SerializedName("created_at")
    var createdAt: Date,
) : Serializable {
}