package com.example.cinema_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var secondTextView: TextView
    private lateinit var thirdTextView: TextView
    private lateinit var firstButton: Button
    private lateinit var secondButton: Button
    private lateinit var thirdButton: Button
    private var textViewColors = TextViewColors(R.color.black, R.color.black, R.color.black)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setClickListeners()

        savedInstanceState?.let { state ->
            textViewColors = state.getParcelable(STATE_COLOR_TEXT)!!
            textView.setTextColor(ContextCompat.getColor(this, textViewColors.firstTextColorId))
            secondTextView.setTextColor(ContextCompat.getColor(this, textViewColors.secondTextColorId))
            thirdTextView.setTextColor(ContextCompat.getColor(this, textViewColors.thirdTextColorId))
        }
    }

    private fun setClickListeners() {
        firstButton.setOnClickListener {
            cinemaClicked(CinemaHolder.cinema1, textView)
        }
        secondButton.setOnClickListener {
            cinemaClicked(CinemaHolder.cinema2, secondTextView)
        }
        thirdButton.setOnClickListener {
            cinemaClicked(CinemaHolder.cinema3, thirdTextView)
        }
    }

    private fun initViews() {
        textView = findViewById(R.id.textView1)
        secondTextView = findViewById(R.id.textView2)
        thirdTextView = findViewById(R.id.textView3)
        firstButton = findViewById(R.id.button1)
        secondButton = findViewById(R.id.button2)
        thirdButton = findViewById(R.id.button3)
    }

    private fun cinemaClicked(cinema: Cinema, textView: TextView) {
        val intent = Intent(this, CinemaActivity::class.java)
        textView.setTextColor(resources.getColor(R.color.refTextColor))
        when (cinema.id) {
            1 -> textViewColors.firstTextColorId = R.color.refTextColor
            2 -> textViewColors.secondTextColorId = R.color.refTextColor
            3 -> textViewColors.thirdTextColorId = R.color.refTextColor
        }
        intent.putExtra(EXTRA_CINEMA, cinema)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE_COLOR_TEXT, textViewColors)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_FIRST_USER) {
                if (data != null) {
                    Log.d("TAG_REQUEST", "like: " + data.getBooleanExtra(CinemaActivity.RESULT_LIKE, false)
                            + " comment: " + data.getStringExtra(CinemaActivity.RESULT_COMMENT))
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val EXTRA_CINEMA = "extra_cinema"
        const val STATE_COLOR_TEXT = "color_text"
        const val REQUEST_CODE = 123
    }
}