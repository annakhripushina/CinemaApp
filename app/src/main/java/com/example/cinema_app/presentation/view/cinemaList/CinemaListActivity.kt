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
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.*
import com.example.cinema_app.data.CinemaHolder
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.viewmodel.CinemaListViewModel
import com.google.android.material.snackbar.Snackbar


class CinemaListActivity : Fragment() {
    companion object {
        const val EXTRA_CINEMA = "extra_cinema"
        const val ITEM_CINEMA = "item_cinema"
        const val CINEMA_LIST = "cinema_list"
        const val EXTRA_FAV = "extra_fav"
        const val FAVOURITE_LIST = "favourite_list"
    }

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
       /* if (savedInstanceState == null)
            setFragmentResultListener(FavouriteActivity.RESULT_FAVOURITE_LIST) { _, result ->
                favouriteList = result.getParcelableArrayList(FAVOURITE_LIST)!!
            }
        else {
            CinemaHolder.cinemaList = savedInstanceState.getParcelableArrayList(CINEMA_LIST)!!
            favouriteList = savedInstanceState.getParcelableArrayList(FAVOURITE_LIST)!!
        }*/

        initRecycler()
        onActivityResult()
    }

    override fun onStop() {
        /*setFragmentResult(
            EXTRA_FAV,
            Bundle().apply {
                putParcelableArrayList(FAVOURITE_LIST, favouriteList)
            }
        )*/
        viewModel.onSetFavouriteList(favouriteList)
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(CINEMA_LIST, CinemaHolder.cinemaList)
        outState.putParcelableArrayList(FAVOURITE_LIST, favouriteList)
    }


    private fun onActivityResult() {
        /*setFragmentResultListener(CinemaActivity.RESULT_ACTION) { _, result ->
            hasLiked = result.getBoolean(CinemaActivity.RESULT_LIKE)
            comment = result.getString(CinemaActivity.RESULT_COMMENT).toString()
            Log.d(
                "TAG_REQUEST",
                "like: $hasLiked comment: $comment"
            )
        }*/
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
                    cinemaItem.titleColor = Color.MAGENTA

//                    setFragmentResult(
//                        EXTRA_CINEMA,
//                        Bundle().apply {
//                            putParcelable(ITEM_CINEMA, cinemaItem)
//                        }
//                    )

                    viewModel.onSetCinemaItem(cinemaItem)

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                        .addToBackStack("cinemaActivity")
                        .commit()
                }

                override fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
                    if (cinemaItem !in favouriteList) {
                        //favouriteList.add(cinemaItem)
                        viewModel.onAddFavouriteItem(favouriteList,cinemaItem)

                        val snackAddFavourite = Snackbar.make(
                            itemView,
                            "Фильм добавлен в список избранного",
                            Snackbar.LENGTH_LONG
                        )

                        snackAddFavourite.setAction("Отмена") {
                            //favouriteList.remove(cinemaItem)
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