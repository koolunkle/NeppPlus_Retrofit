package com.neppplus.retrofitlibrarypractice.datas

class DataResponse(
    var token: String,
    var user: UserData,
    // 아래 변수는 상품 목록에서만 사용
    var products: List<ProductData>
) {
}