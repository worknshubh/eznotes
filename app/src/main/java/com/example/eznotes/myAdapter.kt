package com.example.eznotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter(var context: Context, var userlist:List<userdata>): RecyclerView.Adapter<myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.my_items,parent,false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val user = userlist[position]
        holder.title.text = user.title
        holder.desc.text = user.desc
        holder.timing.text = user.timestamp?.toDate().toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(context,notes::class.java)
            context.startActivity(intent)
        }
    }

}

class myViewHolder(View:View): RecyclerView.ViewHolder(View){
    var title = View.findViewById<TextView>(R.id.titleview)
    var desc = View.findViewById<TextView>(R.id.contview)
    var timing = View.findViewById<TextView>(R.id.timestamp)

}