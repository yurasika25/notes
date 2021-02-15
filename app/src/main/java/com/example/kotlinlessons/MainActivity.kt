package com.example.kotlinlessons

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlessons.db.MyDbManger

class MainActivity : AppCompatActivity() {

//    val myDbManger = MyDbManger(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickNew(view: View) {
        val clickNew = Intent (this,EditActivity::class.java)
        startActivity(clickNew)
    }
}

//    fun onClickSave(view: View) {
////        myDbManger.openDb()
////        val dataList = myDbManger.readDbData()
////        for(item in dataList){
//
//        }



//    override fun onDestroy() {
//        super.onDestroy()
//        myDbManger.closeDb()
//    }

