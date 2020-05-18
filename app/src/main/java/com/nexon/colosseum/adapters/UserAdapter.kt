package com.nexon.colosseum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nexon.colosseum.R
import com.nexon.colosseum.datas.User

class UserAdapter (context : Context, resID:Int, list:ArrayList<User>)  : ArrayAdapter<User>(context, resID, list)  {
    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow.let {

        }.let {
            tempRow = inf.inflate(R.layout.user_list_item, null)
        }

        val row = tempRow!!
        val data = mList.get(position)

        val userEmail = row.findViewById<TextView>(R.id.userNickNameEmail)

        userEmail.text = "${data.nickName}/${data.email}"

        return  row
    }


}