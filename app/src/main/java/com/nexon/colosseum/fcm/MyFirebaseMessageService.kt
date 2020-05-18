package com.nexon.colosseum.fcm

import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.logging.Handler

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
        Log.d("푸시 제목", message?.notification?.title)
        Log.d("푸시 내용", message?.notification?.body.toString())
        android.os.Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message?.notification?.body.toString(), Toast.LENGTH_SHORT).show()

        }
}

}