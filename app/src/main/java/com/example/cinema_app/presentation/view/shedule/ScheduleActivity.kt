package com.example.cinema_app.presentation.view.shedule

import android.content.res.Configuration
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
import com.example.cinema_app.dagger.component.DaggerViewModelComponent
import com.example.cinema_app.dagger.module.viewmodel.CinemaViewModelFactory
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.DateTimePickerUtil
import com.example.cinema_app.service.AlarmService
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class ScheduleActivity : Fragment(), DateTimePickerUtil {
    @Inject
    lateinit var viewModelFactory: CinemaViewModelFactory
    lateinit var viewModel: ScheduleViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var alarmService: AlarmService

    private val adapter = ScheduleAdapter(object : ScheduleAdapter.ScheduleClickListener {
        override fun onEditClick(cinemaItem: Cinema, itemView: View, position: Int) {
            clickButtonScheduleAlarm(
                requireActivity().supportFragmentManager,
                cinemaItem,
                viewModel,
                alarmService,
                requireContext(),
                view
            )
        }

        override fun onDeleteClick(cinemaItem: Cinema, position: Int) {
            viewModel.deleteScheduleCinema(cinemaItem.originalId)
            view?.let { isEmptyList(it) }

            val snackDelete =
                Snackbar.make(view!!, "Напоминание удалено", Snackbar.LENGTH_LONG)
            snackDelete.show()
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CinemaApp.appComponentViewModel.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)
        return inflater.inflate(
            R.layout.activity_schedule,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScheduleRecycler(view)
        initAlarmService()
        viewModel.onGetSchedule().observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                viewModel.allSchedule.value
                    ?.forEach {
                        viewModel.updateDateViewed(it.dateViewed, it.originalId)
                    }
            }
        })

        viewModel.onGetScheduleCinema().observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setItems(list)
                isEmptyList(view)
            }
        })
    }

    private fun initAlarmService() {
        alarmService = AlarmService(requireContext())
    }

    private fun isEmptyList(view: View) {
        if (adapter.itemCount == 0) {
            view.findViewById<TextView>(R.id.emptyList).visibility = VISIBLE
        } else view.findViewById<TextView>(R.id.emptyList).visibility = INVISIBLE
    }

    private fun initScheduleRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewSchedule)
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