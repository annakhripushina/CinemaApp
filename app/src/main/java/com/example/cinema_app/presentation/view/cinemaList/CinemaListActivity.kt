package com.example.cinema_app.presentation.view.cinemaList

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema_app.CinemaListGetter
import com.example.cinema_app.MyItemDecorator
import com.example.cinema_app.R
import com.example.cinema_app.dagger.CinemaApp
import com.example.cinema_app.dagger.module.viewmodel.CinemaViewModelFactory
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.utils.InternalLog
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class CinemaListActivity : Fragment() {
    @Inject
    lateinit var viewModelFactory: CinemaViewModelFactory
    lateinit var viewModel: CinemaListViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var comment: String = ""
    private var hasLiked: Boolean = false
    private lateinit var swipeContainer: SwipeRefreshLayout
    private var cinemaListGetter = CinemaListGetter()

    private val adapter = CinemaAdapter(object : CinemaAdapter.CinemaClickListener {
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

        override fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int) {
            viewModel.onAddFavouriteCinema(cinemaItem)

            val snackAddFavourite = Snackbar.make(
                itemView,
                getString(R.string.favouriteAddSnackbar),
                Snackbar.LENGTH_LONG
            )
            snackAddFavourite.setAction(getString(R.string.cancelText)) {
                viewModel.onRemoveFavouriteCinema(cinemaItem)
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
    ): View? {
        CinemaApp.appComponentViewModel.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CinemaListViewModel::class.java)
        return inflater.inflate(
            R.layout.activity_list,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pullToRefresh(view)
        initRecycler(view)

        viewModel.onGetAllCinema().observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setItems(list as ArrayList<Cinema>)
                progressBar.visibility = INVISIBLE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            val snackUpdateList = Snackbar.make(
                view,
                error,
                Snackbar.LENGTH_LONG
            )
            snackUpdateList.setAction(getString(R.string.updateText)) {
                viewModel.onGetCinemaListPage()
                snackUpdateList.dismiss()
            }
            snackUpdateList.anchorView = activity?.findViewById(R.id.navigate)
            snackUpdateList.show()

        })

        onActivityResult()
        searchCinema(view)
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
                    cinemaListGetter.onGetCinemaListPage(viewModel, view)
                }
            }
        })
    }

    private fun onActivityResult() {
        viewModel.onGetHasLiked()
        hasLiked = viewModel.hasLiked
        comment = viewModel.comment
        InternalLog.d("TAG_REQUEST", "like: $hasLiked comment: $comment")
    }

    private fun pullToRefresh(view: View) {
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            viewModel.onGetCinemaList()
            swipeContainer.isRefreshing = false
        }
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

    private fun searchCinema(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.searchView)

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    subscriber.onNext(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    subscriber.onNext(query)
                    return false
                }
            })
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                viewModel.onSearchCinema(text)
                    .observe(viewLifecycleOwner, Observer { list ->
                        list?.let {
                            adapter.setItems(list as ArrayList<Cinema>)
                        }
                    })
            }
    }


}