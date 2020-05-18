package com.nexon.colosseum.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService : FirebaseMessagingService() {

//  디바이스 토큰이 발급되는 시점에 실행되는 함수
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d("신규발급 토큰", token)
    }

//  푸시알림이 수신되면 실행되는 함수
    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        Log.d("메세지 수신", "완료")
    }

}