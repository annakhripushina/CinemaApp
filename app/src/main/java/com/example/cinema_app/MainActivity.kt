package com.example.cinema_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        val cinema1: Cinema = Cinema(R.id.textView1,R.string.img1Description, R.drawable.img_1, false)
        val cinema2: Cinema = Cinema(R.id.textView2,R.string.img2Description, R.drawable.img_2, false)
        val cinema3: Cinema = Cinema(R.id.textView3,R.string.img3Description, R.drawable.img_3, false)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun onDetailClick(idButton: Int, cinema: Cinema){
            findViewById<Button>(idButton).setOnClickListener{
                val intent = Intent(this, CinemaActivity::class.java)
                findViewById<TextView>(cinema.idTitle).setTextColor(Color.parseColor(getString(R.string.refTextColor)))
                intent.putExtra(EXTRA_IMAGE, cinema.idImage)
                intent.putExtra(EXTRA_DESC, cinema.idDescription)
                intent.putExtra(EXTRA_TITLE, findViewById<TextView>(cinema.idTitle).text)
                intent.putExtra(EXTRA_LIKE, cinema.like)
                startActivityForResult(intent, 123)
            }
        }

        onDetailClick(R.id.button1, cinema1)
        onDetailClick(R.id.button2, cinema2)
        onDetailClick(R.id.button3, cinema3)

        textView = findViewById(R.id.textView1)

        savedInstanceState?.let { state ->
           textView.setTextColor(Color.parseColor(state.getString(STATE_COLOR_TEXT, "")))
        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView.setTextColor(Color.parseColor(savedInstanceState.getString(STATE_COLOR_TEXT, "")))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val intColor = textView.currentTextColor
        val hexColor = Integer.toHexString(intColor).substring(2)
        outState.putString(STATE_COLOR_TEXT, "#$hexColor")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==123) {
            if (resultCode == RESULT_CANCELED) {
                data?.getBooleanExtra(CinemaActivity.RESULT_LIKE, false)?.let { input ->
                    Toast.makeText(this, input.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("LIKE3", input.toString())
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data)
    }
    companion object{
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_LIKE = "extra_like"
        const val STATE_COLOR_TEXT = "color_text"
    }

}