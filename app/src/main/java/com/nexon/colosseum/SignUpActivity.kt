package com.nexon.colosseum

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nexon.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        email_dupe_check_btn.setOnClickListener {
            val email = emailEdt.text.toString()
            val dupeCheckType = "EMAIL"

            ServerUtil.getRequestIsEmailDuplecated(mContext, dupeCheckType ,email, object : ServerUtil.JsonResponseHandler{
                @SuppressLint("ResourceAsColor")
                override fun onResponse(json: JSONObject) {
                    Log.d("중복 확인 결과", json.toString())
                    val code = json.getInt("code")
                    if (code == 200) {
                        runOnUiThread {
                            isDupeEmailText.setTextColor(resources.getColor(R.color.azule))
                            isDupeEmailText.setText(R.string.email_is_not_dupe_text)
                        }
                    } else {
                        runOnUiThread {
                            isDupeEmailText.setTextColor(resources.getColor(R.color.grapefruit))
                            isDupeEmailText.setText(R.string.email_is_dupe_text)
                        }
                    }

                }

            })

        }

    }

    override fun setValues() {

    }


}
