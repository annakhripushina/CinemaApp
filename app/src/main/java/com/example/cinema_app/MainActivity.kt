package com.example.cinema_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        val cinema1 = Cinema(R.string.img1TextView, R.string.img1Description, R.drawable.img_1, false)
        val cinema2 = Cinema(R.string.img2TextView, R.string.img2Description, R.drawable.img_2, false)
        val cinema3 = Cinema(R.string.img3TextView, R.string.img3Description, R.drawable.img_3, false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun onDetailClick(idButton: Int, idTextView: Int, cinema: Cinema){
            findViewById<Button>(idButton).setOnClickListener{
                val intent = Intent(this, CinemaActivity::class.java)
                findViewById<TextView>(idTextView).setTextColor(Color.parseColor(getString(R.string.refTextColor)))
                intent.putExtra(EXTRA_CINEMA, cinema)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }

        onDetailClick(R.id.button1, R.id.textView1, cinema1)
        onDetailClick(R.id.button2, R.id.textView2, cinema2)
        onDetailClick(R.id.button3, R.id.textView3, cinema3)

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
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_FIRST_USER) {
                if (data != null) {
                    Log.d("TAG_REQUEST", "like: " + data.getBooleanExtra(CinemaActivity.RESULT_LIKE, false)
                            + " comment: " + data.getStringExtra(CinemaActivity.RESULT_COMMENT))
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        const val EXTRA_CINEMA = "extra_cinema"
        const val STATE_COLOR_TEXT = "color_text"
        const val REQUEST_CODE = 123
    }

}