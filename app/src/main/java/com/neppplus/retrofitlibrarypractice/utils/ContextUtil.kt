package com.neppplus.retrofitlibrarypractice.utils

import android.content.Context

class ContextUtil {

    companion object {

        // 어떤 파일? 파일명
        private val prefName = "RetrofitTestPref"

        // 어떤 데이터 항목? 항목명
        private val TOKEN = "TOKEN"

        // 저장 함수 (setter) / 조회 함수 (getter)
        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(TOKEN, token).apply()
        }

        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(TOKEN, "")!!
        }

    }

}