package com.example.cinema_app.presentation.view.cinemaList

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.MyItemDecorator
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.viewmodel.CinemaListViewModel
import com.google.android.material.snackbar.Snackbar


class CinemaListActivity : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var favouriteList: ArrayList<Cinema> = ArrayList()
    private var comment: String = ""
    private var hasLiked: Boolean = false

    private val viewModel: CinemaListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.activity_list,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)

        viewModel.onGetCinemaList()
        favouriteList = viewModel.favouriteList

        initRecycler()
        onActivityResult()
    }

    override fun onStop() {
        viewModel.onSetFavouriteList(favouriteList)
        super.onStop()
    }

     private fun onActivityResult() {
        hasLiked = viewModel.hasLiked
        comment = viewModel.comment
        Log.d(
            "TAG_REQUEST",
            "like: $hasLiked comment: $comment"
        )
    }

    private fun initRecycler() {
        recyclerView.adapter =
            CinemaAdapter(viewModel.cinemaList, object : CinemaAdapter.CinemaClickListener {
                override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    itemView.findViewById<TextView>(R.id.titleView)
                        .setTextColor(Color.MAGENTA)
                    cinemaItem.titleColor = Color.MAGENTA //???

                    viewModel.onSetCinemaItem(cinemaItem)

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                        .addToBackStack("cinemaActivity")
                        .commit()
                }

                override fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    if (cinemaItem !in favouriteList) {
                        viewModel.onAddFavouriteItem(favouriteList,cinemaItem)

                        val snackAddFavourite = Snackbar.make(
                            itemView,
                            "Фильм добавлен в список избранного",
                            Snackbar.LENGTH_LONG
                        )

                        snackAddFavourite.setAction("Отмена") {
                            viewModel.onRemoveFavouriteItem(favouriteList, cinemaItem)
                            snackAddFavourite.dismiss()
                        }

                        snackAddFavourite.anchorView = activity?.findViewById(R.id.navigate)
                        snackAddFavourite.show()
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