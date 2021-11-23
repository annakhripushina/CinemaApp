package com.example.cinema_app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CinemaListActivity : Fragment() {

    companion object {
        const val EXTRA_CINEMA = "extra_cinema"
        const val ITEM_CINEMA = "item_cinema"
        const val CINEMA_LIST = "cinema_list"
        const val EXTRA_FAV = "extra_fav"
        const val FAVOURITE_LIST = "favourite_list"

    }

    private lateinit var recyclerView : RecyclerView
    private var favouriteList: ArrayList<Cinema> = ArrayList()
    private var comment: String = ""
    private var like: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.activity_list,
        container,
        false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        savedInstanceState?.let { state ->
            CinemaHolder.cinemaList = state.getParcelableArrayList(CINEMA_LIST)!!
            favouriteList = state.getParcelableArrayList(FAVOURITE_LIST)!!
        }

        initRecycler()
        onActivityResult()
        onClickFavourite()
    }

  override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(CINEMA_LIST, CinemaHolder.cinemaList)
        outState.putParcelableArrayList(FAVOURITE_LIST, favouriteList)
    }

    private fun onClickFavourite() {
        view?.findViewById<Button>(R.id.buttonFavourite)?.setOnClickListener {

            setFragmentResult(
                EXTRA_FAV,
                Bundle().apply {
                    putParcelableArrayList(FAVOURITE_LIST, favouriteList)
                }
            )

            parentFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, FavouriteActivity())
                .addToBackStack("favouriteActivity")
                .commit()
        }
    }

    private fun onActivityResult(){
        setFragmentResultListener(CinemaActivity.RESULT_ACTION) { _, result ->
            like = result.getBoolean(CinemaActivity.RESULT_LIKE)
            comment = result.getString(CinemaActivity.RESULT_COMMENT).toString()
            Log.d(
                "TAG_REQUEST",
                "like: $like comment: $comment"
            )
        }
    }

    private fun initRecycler() {
        recyclerView.adapter =
            CinemaAdapter(CinemaHolder.cinemaList, object : CinemaAdapter.CinemaClickListener {
                override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    val intent = Intent(view?.context, CinemaListActivity::class.java)
                    itemView.findViewById<TextView>(R.id.titleView)
                        .setTextColor(Color.MAGENTA) //R.color.refTextColor
                    cinemaItem.titleColor = Color.MAGENTA

                   setFragmentResult(
                        EXTRA_CINEMA,
                        Bundle().apply {
                            putParcelable(ITEM_CINEMA, cinemaItem)
                        }
                    )

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, CinemaActivity())
                        .addToBackStack("cinemaActivity")
                        .commit()
                }

                override fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    if (cinemaItem !in favouriteList) {
                        favouriteList.add(cinemaItem)
                        Toast.makeText(
                            view?.context,
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
                recyclerView.layoutManager = GridLayoutManager(view?.context, 2)
            }
            ORIENTATION_PORTRAIT -> {
                recyclerView.layoutManager = GridLayoutManager(view?.context, 1)
            }
        }
    }

}