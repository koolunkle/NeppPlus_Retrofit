package com.neppplus.retrofitlibrarypractice.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

//        실제 푸시 신호가 왔을 때 할 행동 -> p0 변수 : 어떤 메세지? 담아주는 역할

    }

}