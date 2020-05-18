package com.nexon.colosseum.utils

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }

    companion object {

        private val BASE_URL = "http://ec2-15-165-177-142.ap-northeast-2.compute.amazonaws.com"


        fun getUserList(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()
            val urlBuilder = "${BASE_URL}/user".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("device_token", FirebaseInstanceId.getInstance().token)
//            urlBuilder.addEncodedQueryParameter("os", "Android")

            val urlStr = urlBuilder.build().toString()


            val request = Request.Builder()
                .url(urlStr)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()
                    val json = JSONObject(body)
                    handler?.onResponse(json)
                }
            })
        }

        fun postSendPushMessage(context: Context, id: Int, handler: JsonResponseHandler?) {
            val client = OkHttpClient()
            val urlStr = "${BASE_URL}/user_check"

            val formBody = FormBody.Builder()
                .add("user_id", "${id}")
                .build()

            val request = Request.Builder()
                .url(urlStr)
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    val body = response.body!!.string()
                    val json = JSONObject(body)

                    handler?.onResponse(json)


                }

            })


        }

        fun postRequestLogin(
            context: Context,
            id: String,
            pw: String,
            handler: JsonResponseHandler?
        ) {

            val client = OkHttpClient()
            val urlStr = "${BASE_URL}/user"

            val formBody = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

            val request = Request.Builder()
                .url(urlStr)
                .post(formBody)
//                .header()  => API가 헤더를 요구하면 추가해야함.
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    val body = response.body!!.string()
                    val json = JSONObject(body)

                    handler?.onResponse(json)


                }

            })


        }

        fun getRequestIsEmailDuplecated(context: Context, type : String , value:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", value)

            val urlStr = urlBuilder.build().toString()


            val request = Request.Builder()
                .url(urlStr)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()
                    val json = JSONObject(body)
                    handler?.onResponse(json)
                }
            })
        }

        fun getRequestMainInfo(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()
            val urlBuilder = "${BASE_URL}/main_info".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("device_token", FirebaseInstanceId.getInstance().token)
            urlBuilder.addEncodedQueryParameter("os", "Android")

            val urlStr = urlBuilder.build().toString()


            val request = Request.Builder()
                .url(urlStr)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()
                    val json = JSONObject(body)
                    handler?.onResponse(json)
                }
            })
        }

        fun getRequestUserCategory(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()
            val urlBuilder = "${BASE_URL}/system/user_category".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("device_token", "임시기기토큰")
//            urlBuilder.addEncodedQueryParameter("os", "Android")

            val urlStr = urlBuilder.build().toString()

//            Log.d("완성된주소", urlStr)

            val request = Request.Builder()
                .url(urlStr)
//                .header("X-Http-Token", token)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()
                    val json = JSONObject(body)
                    handler?.onResponse(json)
                }
            })
        }

    }



}