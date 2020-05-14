package com.nexon.colosseum

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.nexon.colosseum.utils.ContextUtil
import com.nexon.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {
    val REQ = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        changeProfileImgBtn.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_PICK)
            myIntent.setType("image/*")
            myIntent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(myIntent, REQ)
        }

    }

    override fun setValues() {

    }

    override fun onResume() {
        super.onResume()
        val myToken = ContextUtil.getUserToken(mContext)
        Log.d("토큰", myToken)

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {
                Log.d("메인 정보 불러오기 결과", json.toString())
                val code = json.getInt("code")

                if (code == 200) {
                    val data = json.getJSONObject("data")
                    val user = data.getJSONObject("user")
                    val topic = data.getJSONObject("topic")
                    val nickName = user.getString("nick_name")
                    val topicName = topic.getString("title")

                    runOnUiThread {
                        myNickNameText.text = nickName
                        thisWeekTopicTitle.text = topicName
                    }

                }

            }

        })
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ) {
            if (resultCode == Activity.RESULT_OK) {
                Glide.with(mContext).load(data?.data).into(myProfileImg)

            }
        }

    }

}
