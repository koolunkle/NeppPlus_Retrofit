package com.neppplus.retrofitlibrarypractice.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Date> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {

//        서버가 주는 항목중 -> Date 로 파싱하려는 항목을 일단 String 으로 받아오자
        val dateStr = json?.asString

//        서버가 준 String -> Date 형식으로 변환 (parse)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.parse(dateStr)!!

//        파싱 결과로 완성된 date 선정
        return date

    }

}