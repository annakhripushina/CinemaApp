package com.example.cinema_app.presentation.view.cinemaList

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.MyItemDecorator
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.viewmodel.CinemaListViewModel
import com.google.android.material.snackbar.Snackbar


class CinemaListActivity : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel: CinemaListViewModel by activityViewModels()
    private var comment: String = ""
    private var hasLiked: Boolean = false

    private val adapter = CinemaAdapter(object : CinemaAdapter.CinemaClickListener {
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

        override fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
            viewModel.onAddFavouriteItem(cinemaItem)

            val snackAddFavourite = Snackbar.make(
                itemView,
                "Фильм добавлен в список избранного",
                Snackbar.LENGTH_LONG
            )
            snackAddFavourite.setAction("Отмена") {
                viewModel.onRemoveFavouriteItem(cinemaItem)
                snackAddFavourite.dismiss()
            }
            snackAddFavourite.anchorView = activity?.findViewById(R.id.navigate)
            snackAddFavourite.show()
        }
    })

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

        initRecycler(view)

        viewModel.allCinema.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setItems(list as ArrayList<Cinema>)
                progressBar.visibility = INVISIBLE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

        onActivityResult()

    }

    private fun onActivityResult() {
        hasLiked = viewModel.hasLiked
        comment = viewModel.comment
        Log.d(
            "TAG_REQUEST",
            "like: $hasLiked comment: $comment"
        )
    }

    private fun initRecycler(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = VISIBLE

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        setGridByOrientation(resources.configuration.orientation)
        recyclerView.addItemDecoration(MyItemDecorator())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.onGetCinemaListPages()
                } else if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Toast.makeText(context, "Обновляем...", Toast.LENGTH_SHORT).show()
                    viewModel.onGetCinemaList()
                }
            }
        })
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