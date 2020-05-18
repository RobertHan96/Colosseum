package com.nexon.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.nexon.colosseum.adapters.UserAdapter
import com.nexon.colosseum.datas.User
import com.nexon.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_user_list.*
import org.json.JSONObject

class UserListActivity : BaseActivity() {
    val userList = ArrayList<User>()
    private lateinit var mUserAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mUserAdapter = UserAdapter(mContext, R.layout.user_list_item, userList)
        userListView.adapter = mUserAdapter

        ServerUtil.getUserList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                Log.d("메인 정보 불러오기 결과", json.toString())
                val code = json.getInt("code")
                if (code == 200) {
                    val data = json.getJSONObject("data")
                    val users = data.getJSONArray("users")

                    for (user in 0..users.length()-1) {
                        val userJson = users.getJSONObject(user)
                        userList.add(User.getUserFromJson(userJson))
                    }

                    runOnUiThread {
                        mUserAdapter.notifyDataSetChanged()

                    }



                }
            }

        })



    }

}
