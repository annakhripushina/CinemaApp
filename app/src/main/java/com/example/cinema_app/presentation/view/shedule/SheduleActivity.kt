package com.example.cinema_app.presentation.view.Schedule

import android.content.res.Configuration
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
import com.example.cinema_app.presentation.DateTimePickerUtil
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.example.cinema_app.presentation.viewmodel.CinemaViewModelFactory
import com.example.cinema_app.service.AlarmService
import com.google.android.material.snackbar.Snackbar


class ScheduleActivity : Fragment(), DateTimePickerUtil {
    private val viewModel: CinemaViewModel by activityViewModels {
        CinemaViewModelFactory(
            requireActivity().application
        )
    }
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
        viewModel.allSchedule.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                val arr = viewModel.allSchedule.value //as List<ScheduleCinema>
                if (arr != null) {
                    arr.forEach {
                        viewModel.updateDateViewed(it.dateViewed, it.originalId)
                    }
                }
            }
        })

        viewModel.allScheduleCinema.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setItems(list as ArrayList<Cinema>)
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