package com.nazirov.android_graphql.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nazirov.android_graphql.R
import com.nazirov.android_graphql.UsersListQuery
import com.nazirov.android_graphql.activity.MainActivity

class PostAdapter(var context: MainActivity, var items: List<UsersListQuery.User>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = items[position]

        if (holder is UserViewHolder) {
            val tvName = holder.tvName
            val tvRocket = holder.tvRocket
            val tvTwitter = holder.tvTwitter
            val item = holder.item

            tvName.text = user.name
            tvRocket.text = user.rocket
            tvTwitter.text = user.twitter

            item.setOnClickListener {
                context.openUpdateActivity(user)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView
        var tvRocket: TextView
        var tvTwitter: TextView
        var item: LinearLayout

        init {
            tvName = view.findViewById(R.id.tvName)
            tvRocket = view.findViewById(R.id.tvRocket)
            tvTwitter = view.findViewById(R.id.tvTwitter)
            item = view.findViewById(R.id.item_user)
        }
    }
}