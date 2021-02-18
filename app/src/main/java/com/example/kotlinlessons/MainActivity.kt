package com.example.kotlinlessons

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlessons.db.MyAdapter
import com.example.kotlinlessons.db.MyDbManger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val myDbManger = MyDbManger(this)
    val myAdapter = MyAdapter(ArrayList(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManger.closeDb()
    }

    override fun onResume() {
        super.onResume()
        myDbManger.openDb()
        fillAdapter()
    }

    fun onClickNew(view: View) {
        val clickNew = Intent (this,EditActivity::class.java)
        startActivity(clickNew)
    }
    fun init(){
        rcViewId.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(rcViewId)
        rcViewId.adapter = myAdapter

    }
    fun fillAdapter(){
        val list = myDbManger.readDbData()
        myAdapter.updateAdapter(list)
        if(list.size > 0) {
            tvEmpty.visibility = View.GONE
        }else {
            tvEmpty.visibility = View.VISIBLE
        }
    }
    private fun getSwapMg(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.
        SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removeItem(viewHolder.adapterPosition,myDbManger)
            }
        })
    }
}

