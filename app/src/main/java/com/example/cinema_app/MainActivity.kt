package com.example.cinema_app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CINEMA = "extra_cinema"
        const val CINEMA_LIST = "cinema_list"
        const val FAVOURITE_LIST = "favourite_list"
        const val REQUEST_CODE = 123
        const val REQUEST_FAVOURITE = 111
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private var favouriteList: ArrayList<Cinema> = ArrayList()

    @SuppressLint("UseCompatLoadingForDrawables")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let { state ->
            CinemaHolder.cinemaList = state.getParcelableArrayList(CINEMA_LIST)!!
            favouriteList = state.getParcelableArrayList(FAVOURITE_LIST)!!
        }
        initRecycler()
        onClickFavourite()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(CINEMA_LIST, CinemaHolder.cinemaList)
        outState.putParcelableArrayList(FAVOURITE_LIST, favouriteList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        CinemaHolder.cinemaList = savedInstanceState.getParcelableArrayList(CINEMA_LIST)!!
        favouriteList = savedInstanceState.getParcelableArrayList(FAVOURITE_LIST)!!
    }

    private fun onClickFavourite() {
        findViewById<Button>(R.id.buttonFavourite).setOnClickListener {
            val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
            intent.putParcelableArrayListExtra("extra_fav", favouriteList)
            startActivityForResult(intent, REQUEST_FAVOURITE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_FIRST_USER) {
                if (data != null) {
                    Log.d(
                        "TAG_REQUEST",
                        "like: " + data.getBooleanExtra(CinemaActivity.RESULT_LIKE, false)
                                + " comment: " + data.getStringExtra(CinemaActivity.RESULT_COMMENT)
                    )
                }
            }
        } else if (requestCode == REQUEST_FAVOURITE) {
            if (resultCode == RESULT_FIRST_USER) {
                if (data != null) {
                    favouriteList =
                        data.getParcelableArrayListExtra<Cinema>(FavouriteActivity.RESULT_FAVOURITE_LIST) as ArrayList<Cinema>

                }
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        DialogBack().show(supportFragmentManager, "dialog")
    }

    fun superOnBackPressed() {
        super.onBackPressed()
    }

    private fun initRecycler() {
        recyclerView.adapter =
            CinemaAdapter(CinemaHolder.cinemaList, object : CinemaAdapter.CinemaClickListener {
                override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    val intent = Intent(this@MainActivity, CinemaActivity::class.java)
                    itemView.findViewById<TextView>(R.id.titleView)
                        .setTextColor(Color.MAGENTA) //R.color.refTextColor
                    cinemaItem.titleColor = Color.MAGENTA
                    intent.putExtra(EXTRA_CINEMA, cinemaItem)
                    intent.putExtra("extra_position", position)
                    startActivityForResult(intent, REQUEST_CODE)
                }

                override fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    if (cinemaItem !in favouriteList) {
                        favouriteList.add(cinemaItem)
                        Toast.makeText(
                            applicationContext,
                            "Фильм добавлен в список избранного",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            )
        setGridByOrientation(resources.configuration.orientation)
        recyclerView.addItemDecoration(MyItemDecorator())
    }

    private fun setGridByOrientation(orientation: Int) {
        when (orientation) {
            ORIENTATION_LANDSCAPE -> {
                recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
            }
            ORIENTATION_PORTRAIT -> {
                recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)
            }
        }
    }

}