package com.nexon.colosseum.datas

import org.json.JSONObject

class User {

    companion object {
        fun getUserFromJson(json : JSONObject) : User {
            val user = User()
            user.id = json.getInt("id")
            user.nickName = json.getString("nick_name")
            user.email = json.getString("email")

            return  user

        }

    }
    var id = 0
    var nickName = ""
    var email = ""
}