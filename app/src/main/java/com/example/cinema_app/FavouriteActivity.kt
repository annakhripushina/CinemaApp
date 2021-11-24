package com.example.cinema_app

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class FavouriteActivity : Fragment() {

    companion object {
        const val RESULT_FAVOURITE_LIST = "FAVOURITE_LIST"
    }

    private lateinit var recyclerView: RecyclerView//val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerViewFavourite) }
    private lateinit var favouriteList: ArrayList<Cinema>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.activity_favorite,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFavourite)
        setGridByOrientation(resources.configuration.orientation)
        recyclerView.addItemDecoration(MyItemDecorator())

        if (savedInstanceState == null)
            setFragmentResultListener(CinemaListActivity.EXTRA_FAV) { _, result ->
                favouriteList = result.getParcelableArrayList(CinemaListActivity.FAVOURITE_LIST)!!
                isEmptyList(view)
            }
        else {
            favouriteList = savedInstanceState.getParcelableArrayList("FAVOURITE_LIST")!!
            isEmptyList(view)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("FAVOURITE_LIST", favouriteList)
    }

    private fun isEmptyList(view: View) {
        if (favouriteList.isEmpty()) {
            view.findViewById<TextView>(R.id.emptyList).visibility = VISIBLE
        } else view.findViewById<TextView>(R.id.emptyList).visibility = INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        initFavoriteRecycler()
    }

    override fun onStop() {
        setFragmentResult(
            RESULT_FAVOURITE_LIST,
            Bundle().apply {
                putParcelableArrayList(CinemaListActivity.FAVOURITE_LIST, favouriteList)
            }
        )
        super.onStop()

    }

    private fun initFavoriteRecycler() {
        recyclerView.adapter =
            FavouriteAdapter(favouriteList, object : FavouriteAdapter.FavouriteClickListener {
                override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    itemView.findViewById<TextView>(R.id.titleView)
                        .setTextColor(Color.MAGENTA) //R.color.refTextColor
                    cinemaItem.titleColor = Color.MAGENTA
                    setFragmentResult(
                        CinemaListActivity.EXTRA_CINEMA,
                        Bundle().apply {
                            putParcelable(CinemaListActivity.ITEM_CINEMA, cinemaItem)
                        }
                    )

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, CinemaActivity())
                        .addToBackStack("cinemaActivity")
                        .commit()
                }

                override fun onDeleteClick(cinemaItem: Cinema, position: Int) {

                    favouriteList.removeAt(position)
                    recyclerView.adapter?.notifyItemRemoved(position)
                    view?.let { isEmptyList(it) }

                    val snackDeleteFavourite =
                        Snackbar.make(view!!, "Фильм удален из Избранного", Snackbar.LENGTH_LONG)

                    snackDeleteFavourite.setAction("Отмена") {
                        favouriteList.add(position, cinemaItem)
                        recyclerView.adapter?.notifyItemInserted(position)
                        snackDeleteFavourite.dismiss()
                        view?.let { isEmptyList(it) }
                    }
                    snackDeleteFavourite.show()

                }
            }
            )
    }

    private fun setGridByOrientation(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                recyclerView.layoutManager = GridLayoutManager(view?.context, 2)
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                recyclerView.layoutManager = GridLayoutManager(view?.context, 1)
            }
        }
    }

}