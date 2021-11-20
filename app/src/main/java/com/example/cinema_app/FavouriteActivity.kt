package com.example.cinema_app

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FavouriteActivity : AppCompatActivity() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerViewFavourite)}
    private lateinit var favouriteList: ArrayList <Cinema>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setGridByOrientation(resources.configuration.orientation)
        favouriteList = intent.getParcelableArrayListExtra<Cinema>("extra_fav")!!
        isEmptyList()
        recyclerView.addItemDecoration(MyItemDecorator(this))

    }

    private fun isEmptyList(){
        if (favouriteList.isEmpty()){
            findViewById<TextView>(R.id.emptyList).visibility = VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        initFavoriteRecycler()
    }

    override fun onBackPressed() {
        intent.putParcelableArrayListExtra(RESULT_FAVOURITE_LIST,favouriteList)
        setResult(RESULT_FIRST_USER, intent)
        super.onBackPressed()
    }

    private fun initFavoriteRecycler() {
        recyclerView.adapter = FavouriteAdapter(favouriteList, object : FavouriteAdapter.FavouriteClickListener{
            override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                val intent = Intent(this@FavouriteActivity, CinemaActivity::class.java)
                itemView.findViewById<TextView>(R.id.titleView).setTextColor(Color.MAGENTA) //R.color.refTextColor
                cinemaItem.titleColor = Color.MAGENTA
                intent.putExtra(MainActivity.EXTRA_CINEMA, cinemaItem)
                intent.putExtra("extra_position", position)
                startActivityForResult(intent, MainActivity.REQUEST_CODE)
            }

            override fun onDeleteClick(cinemaItem: Cinema, position: Int) {
                favouriteList.removeAt(position)
                recyclerView.adapter?.notifyItemRemoved(position)
                isEmptyList()
            }
        }
        )
    }

    private fun setGridByOrientation(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                recyclerView.layoutManager = GridLayoutManager(applicationContext,1)
            }
        }
    }

    companion object {
        const val RESULT_FAVOURITE_LIST = "FAVOURITE_LIST"
    }
}