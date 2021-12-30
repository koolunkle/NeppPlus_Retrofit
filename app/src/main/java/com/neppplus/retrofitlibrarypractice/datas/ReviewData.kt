package com.neppplus.retrofitlibrarypractice.datas

import java.io.Serializable

class ReviewData(
    var id: Int,
    var title: String,
    var content: String,
    var score: Double,
    var user: UserData,
    var product: ProductData,
) : Serializable {
}