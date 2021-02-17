package com.example.kotlinlessons

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlinlessons.db.MyDbManger
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    val myDbManger = MyDbManger(this)


    var tempImageUri = "empty"
    val imageRequestCode = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManger.closeDb()
    }

    override fun onResume() {
        super.onResume()
        myDbManger.openDb()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode) {

            imageMain.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
        }
    }



    fun onClickAddImage(view: View) {
        constraintLayout.visibility = View.VISIBLE
        btn_Image_Add.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        constraintLayout.visibility = View.GONE
        btn_Image_Add.visibility = View.VISIBLE
    }
    fun onClickChooseImage(view: View) {
        val intent = Intent (Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }
    fun onClickSave(view: View){
        val myTitle = editTitle.text.toString()
        val myDesk = editText.text.toString()

        if(myTitle != "" && myDesk != ""){
            myDbManger.insertToDb(myTitle,myDesk,tempImageUri)

        }


    }


}