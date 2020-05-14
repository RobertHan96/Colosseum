package com.nexon.colosseum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nexon.colosseum.utils.ContextUtil
import com.nexon.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }


        autoLoginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            ContextUtil.setAutoLogin(mContext, isChecked)
        }

        loginBtn.setOnClickListener {

            val email = emailEdt.text.toString()
            val pw = passwordEdt.text.toString()

            ServerUtil.postRequestLogin(mContext, email, pw, object  : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    Log.d("로그인응답", json.toString())

                    val code = json.getInt("code")

                    if (code == 200) {

                        val data = json.getJSONObject("data")
                        val token = data.getString("token")

                        ContextUtil.setUserToken(mContext, token)

                        runOnUiThread {
                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }


                    }
                    else {
                        val message = json.getString("message")

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            })


        }

    }

    override fun setValues() {
        autoLoginCheckBox.isChecked = ContextUtil.isAutoLogin(mContext)
    }


}
