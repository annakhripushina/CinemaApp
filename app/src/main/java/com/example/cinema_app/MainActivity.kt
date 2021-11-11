package com.example.cinema_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Parcelable
import android.util.Log
import androidx.core.view.get



class MainActivity : AppCompatActivity() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView)}

    @SuppressLint("UseCompatLoadingForDrawables")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let { state ->
            CinemaHolder.cinemaList = state.getParcelableArrayList(STATE_COLOR_TEXT)!!
        }
        initRecycler()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_COLOR_TEXT, CinemaHolder.cinemaList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        CinemaHolder.cinemaList = savedInstanceState.getParcelableArrayList(STATE_COLOR_TEXT)!!
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

    private fun initRecycler() {
        recyclerView.adapter = CinemaAdapter(CinemaHolder.cinemaList, object : CinemaAdapter.CinemaClickListener{
            override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                val intent = Intent(this@MainActivity, CinemaActivity::class.java)
                itemView.findViewById<TextView>(R.id.titleView).setTextColor(Color.MAGENTA) //R.color.refTextColor
                cinemaItem.titleColor = Color.MAGENTA
                intent.putExtra(EXTRA_CINEMA, cinemaItem)
                intent.putExtra("extra_position", position)
                startActivityForResult(intent, REQUEST_CODE)
            }
            override fun onFavoriteClick(cinemaItem: Cinema, position: Int) {
            }
        }
        )
    }
    companion object {
        const val EXTRA_CINEMA = "extra_cinema"
        const val STATE_COLOR_TEXT = "color_text"
        const val REQUEST_CODE = 123
    }
}