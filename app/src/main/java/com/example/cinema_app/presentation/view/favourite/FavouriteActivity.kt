package com.example.cinema_app.presentation.view.favourite

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
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.MyItemDecorator
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.cinemaList.CinemaActivity
import com.example.cinema_app.presentation.viewmodel.CinemaListViewModel
import com.google.android.material.snackbar.Snackbar


class FavouriteActivity : Fragment() {
    private val viewModel: CinemaListViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
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
        recyclerView = view.findViewById(R.id.recyclerViewFavourite)
        setGridByOrientation(resources.configuration.orientation)
        recyclerView.addItemDecoration(MyItemDecorator())

        favouriteList = viewModel.favouriteList
        isEmptyList(view)

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
        viewModel.onSetFavouriteList(favouriteList)
        super.onStop()
    }

    private fun initFavoriteRecycler() {
        recyclerView.adapter =
            FavouriteAdapter(viewModel.favouriteList, object : FavouriteAdapter.FavouriteClickListener {
                override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    itemView.findViewById<TextView>(R.id.titleView)
                        .setTextColor(Color.MAGENTA)
                    cinemaItem.titleColor = Color.MAGENTA

                    viewModel.onSetCinemaItem(cinemaItem)

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                        .addToBackStack("cinemaActivity")
                        .commit()
                }

                override fun onDeleteClick(cinemaItem: Cinema, position: Int) {
                    viewModel.onRemoveFavouritePosition(favouriteList, position)
                    recyclerView.adapter?.notifyItemRemoved(position)
                    view?.let { isEmptyList(it) }

                    val snackDeleteFavourite =
                        Snackbar.make(view!!, "Фильм удален из Избранного", Snackbar.LENGTH_LONG)

                    snackDeleteFavourite.setAction("Отмена") {
                        viewModel.onAddFavouritePosition(favouriteList, position, cinemaItem)
                        recyclerView.adapter?.notifyItemInserted(position)
                        snackDeleteFavourite.dismiss()
                        view?.let { isEmptyList(it) }
                    }
                    snackDeleteFavourite.show()
                }
            })
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