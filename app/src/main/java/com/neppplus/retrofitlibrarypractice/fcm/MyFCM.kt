package com.neppplus.retrofitlibrarypractice.fcm

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

//        (앱이 켜진 상태에서) 실제 푸시 신호가 왔을때 할 행동 -> p0 변수 : 어떤 메세지? 담아주는 역할
        val text = p0.notification!!.title

//        UI 스레드에서 토스트 -> 핸들러 활용
        val myHandler = Handler(Looper.getMainLooper())
        myHandler.post {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }

    }

}