package com.nexon.colosseum

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
        autoLoginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            ContextUtil.setAutoLogin(mContext, isChecked)

        }

        loginBtn.setOnClickListener {
            val email = emailEdt.text.toString()
            val password = passwordEdt.text.toString()

            ServerUtil.postRequestLogin(mContext, email, password, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    Log.d("서버 응답",  json.toString())

                    val code = json.getInt("code")

                    if (code == 200) {
                        val data = json.getJSONObject("data")
                        val token = data.getString("token")
                        ContextUtil.setUserToken(mContext, token)

                        runOnUiThread {
                            Toast.makeText(mContext, resources.getString(R.string.login_success_message), Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        val message = json.getString("message")
                        runOnUiThread{
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
