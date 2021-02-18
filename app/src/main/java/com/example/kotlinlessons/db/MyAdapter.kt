package com.example.kotlinlessons.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlessons.EditActivity
import com.example.kotlinlessons.R

class MyAdapter (listMain:ArrayList<ListItem>,contextM:Context): RecyclerView.Adapter<MyAdapter.MyHolder>(){
    var listArray = listMain
    var context = contextM

    class MyHolder(itemView: View,contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvTime = itemView.findViewById<TextView>(R.id.id_time)
        val context = contextV

        fun setData(item:ListItem){
            tvTitle.text = item.title
            tvTime.text = item.time
            itemView.setOnClickListener {

                val intent = Intent(context,EditActivity::class.java).apply {
                    putExtra(MyIntentConstants.I_TITLE_KEY,item.title)
                    putExtra(MyIntentConstants.I_DESK_KEY,item.desc)
                    putExtra(MyIntentConstants.I_URI_KEY,item.uri)
                    putExtra(MyIntentConstants.I_ID_KEY,item.id)

                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rs_item,parent,false),context)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position))
    }
    fun updateAdapter(listItems:List<ListItem>){
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(post:Int, dbManager:MyDbManger){
        dbManager.removeItemFromDb(listArray[post].id.toString())
        listArray.removeAt(post)
        notifyItemRangeChanged(0,listArray.size)
        notifyItemRemoved(post)
    }

}
