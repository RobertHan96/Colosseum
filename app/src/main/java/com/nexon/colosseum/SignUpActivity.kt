package com.nexon.colosseum

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.nexon.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.emailEdt
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    var isEmailCheckOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        signUpBtn.setOnClickListener {

        }

        emailEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                isEmailCheckOk = false
                isDupeEmailText.setText(R.string.email_dupe_check_text)
                isDupeEmailText.setTextColor(resources.getColor(R.color.veryLightGray))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        email_dupe_check_btn.setOnClickListener {
            val email = emailEdt.text.toString()
            val dupeCheckType = "EMAIL"

            ServerUtil.getRequestIsEmailDuplecated(mContext, dupeCheckType ,email, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    Log.d("중복 확인 결과", json.toString())
                    val code = json.getInt("code")
                    if (code == 200) {
                        isEmailCheckOk = true
                        runOnUiThread {
                            isDupeEmailText.setTextColor(resources.getColor(R.color.azule))
                            isDupeEmailText.setText(R.string.email_is_not_dupe_text)
                        }
                    } else {
                        isEmailCheckOk = false
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
