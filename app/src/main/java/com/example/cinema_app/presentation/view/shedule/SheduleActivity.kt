package com.example.cinema_app.presentation.view.shedule

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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.MyItemDecorator
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.example.cinema_app.presentation.viewmodel.CinemaViewModelFactory
import com.google.android.material.snackbar.Snackbar


class SheduleActivity : Fragment() {
    private val viewModel: CinemaViewModel by activityViewModels {
        CinemaViewModelFactory(
            requireActivity().application
        )
    }
    private lateinit var recyclerView: RecyclerView

    private val adapter = SheduleAdapter(object : SheduleAdapter.SheduleClickListener {
        override fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
            itemView.findViewById<TextView>(R.id.titleView)
                .setTextColor(Color.MAGENTA)
            viewModel.updateTitleColor(Color.MAGENTA, cinemaItem.id!!)
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
                Snackbar.make(view!!, "Фильм удален из Избранного", Snackbar.LENGTH_LONG)

            snackDeleteFavourite.setAction("Отмена") {
                viewModel.onAddFavouriteCinema(cinemaItem)
                snackDeleteFavourite.dismiss()
                view?.let { isEmptyList(it) }
            }
            snackDeleteFavourite.show()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.activity_shedule,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteRecycler(view)
        viewModel.allFavouriteCinema.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setItems(list as ArrayList<Cinema>)
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
        recyclerView = view.findViewById(R.id.recyclerViewShedule)
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