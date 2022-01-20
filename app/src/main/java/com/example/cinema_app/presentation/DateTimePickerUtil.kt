package com.example.cinema_app.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.SheduleCinema
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.example.cinema_app.service.AlarmService
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

interface DateTimePickerUtil {
    fun clickButtonScheduleAlarm(
        fm: FragmentManager,
        cinema: Cinema,
        cinemaViewModel: CinemaViewModel,
        alarmService: AlarmService,
        context: Context,
        view: View?
    ) {
        val calendar = Calendar.getInstance()
        val listenerDate = DatePickerDialog.OnDateSetListener { _, _, _, _ ->
            val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), calendar.timeZone.toZoneId()).toLocalDate()

            val listenerTime = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val scheduleDateTime = localDateTime.atTime(hourOfDay, minute).atZone(ZoneId.systemDefault())
                val alarmTime = scheduleDateTime.toInstant().toEpochMilli()

                cinemaViewModel.insertSheduleCinema(
                    SheduleCinema(
                        cinema.original_id,
                        alarmTime.toString(),
                        cinema.original_id
                    )
                )
                alarmService.setExactAlarm(
                    cinema,
                    alarmTime,
                    cinema.original_id
                )
                val snackDeleteFavourite =
                    Snackbar.make(view!!, "Напоминание сохранено", Snackbar.LENGTH_LONG)
                snackDeleteFavourite.show()
            }

            TimePickerDialog(
                context, listenerTime,
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                true
            ).show()
        }
        DatePickerDialog(
            context,
            listenerDate,
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }
}
