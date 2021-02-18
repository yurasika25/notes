package com.example.kotlinlessons

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kotlinlessons.db.MyDbManger
import com.example.kotlinlessons.db.MyIntentConstants
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    var id = 0
    var isEditState = false
    val myDbManger = MyDbManger(this)
    var tempImageUri = "empty"
    val imageRequestCode = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        getMyIntents()
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
            contentResolver.takePersistableUriPermission(
                data?.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }


    fun onClickAddImage(view: View) {
        constraintLayout.visibility = View.VISIBLE
        btn_Image_Add.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        constraintLayout.visibility = View.GONE
        btn_Image_Add.visibility = View.VISIBLE
        tempImageUri = "empty"
    }

    fun onClickChooseImage(view: View) {
        text_view_add.visibility = View.VISIBLE
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }


    fun onClickSave(view: View) {
        val myTitle = editTitle.text.toString()
        val myDesk = editText.text.toString()

        if (myTitle != "" && myDesk != "") {
            if (isEditState) {
                myDbManger.updateItem(myTitle, myDesk, tempImageUri, id,getTime())
            } else {
                myDbManger.insertToDb(myTitle, myDesk, tempImageUri,getTime())
            }

            finish()
        }
    }
    fun onClickWriteText(view: View) {
        editTitle.isEnabled = true
        editText.isEnabled = true
        text_view_add.isEnabled = true

        bt_text_write.visibility = View.GONE
        btn_Image_Add.visibility = View.VISIBLE

        if(tempImageUri=="empty") return
        imBtnEdit.visibility = View.VISIBLE
        imBtnDelete.visibility = View.VISIBLE

    }
    fun getMyIntents() {
        bt_text_write.visibility = View.GONE
        val i = intent

        if (i != null) {

            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {

                btn_Image_Add.visibility = View.GONE
                editTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                isEditState = true
                editTitle.isEnabled = false
                editText.isEnabled = false
                text_view_add.isEnabled = false
                bt_text_write.visibility = View.VISIBLE
                editText.setText(i.getStringExtra(MyIntentConstants.I_DESK_KEY))
                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)
                if (i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty") {

                    constraintLayout.visibility = View.VISIBLE
                    tempImageUri = i.getStringExtra(MyIntentConstants.I_URI_KEY)!!
                    imageMain.setImageURI(Uri.parse(tempImageUri))
                    imBtnDelete.visibility = View.GONE
                    imBtnEdit.visibility = View.GONE
                    text_view_add.isEnabled = false

                }
            }
        }
    }
    private fun getTime(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yy kk:mm", Locale.getDefault())
        return formatter.format(time)
    }
}