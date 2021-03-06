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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.MyItemDecorator
import com.example.cinema_app.R
import com.example.cinema_app.dagger.CinemaApp
import com.example.cinema_app.dagger.module.viewmodel.CinemaViewModelFactory
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class FavouriteActivity : Fragment() {
    @Inject
    lateinit var viewModelFactory: CinemaViewModelFactory
    lateinit var viewModel: FavouriteViewModel

    private lateinit var recyclerView: RecyclerView

    private val adapter = FavouriteAdapter(object : FavouriteAdapter.FavouriteClickListener {
        override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
            itemView.findViewById<TextView>(R.id.titleView)
                .setTextColor(Color.MAGENTA)
            cinemaItem.id?.let { viewModel.updateTitleColor(Color.MAGENTA, it) }
            viewModel.onSetCinemaItem(cinemaItem)

            parentFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                .addToBackStack("cinemaActivity")
                .commit()
        }

        override fun onDeleteClick(cinemaItem: Cinema, position: Int) {
            viewModel.onRemoveFavouriteCinema(cinemaItem)
            view?.let { isEmptyList(it) }

            val snackDeleteFavourite =
                view?.let {
                    Snackbar.make(
                        it,
                        getString(R.string.favouriteDeleteSnackbar),
                        Snackbar.LENGTH_LONG
                    )
                }

            snackDeleteFavourite?.setAction(getText(R.string.cancelText)) {
                viewModel.onAddFavouriteCinema(cinemaItem)
                snackDeleteFavourite.dismiss()
                view?.let { isEmptyList(it) }
            }
            snackDeleteFavourite?.show()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CinemaApp.appComponentViewModel.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavouriteViewModel::class.java)
        return inflater.inflate(
            R.layout.activity_favorite,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteRecycler(view)
        viewModel.onGetFavouriteCinema().observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setItems(list)
                isEmptyList(view)
            }
        })
    }

    private fun isEmptyList(view: View) {
        if (adapter.itemCount == 0) {
            view.findViewById<TextView>(R.id.emptyList).visibility = VISIBLE
        } else view.findViewById<TextView>(R.id.emptyList).visibility = INVISIBLE
    }

    private fun initFavoriteRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewFavourite)
        setGridByOrientation(resources.configuration.orientation)
        recyclerView.addItemDecoration(MyItemDecorator())
        recyclerView.adapter = adapter
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