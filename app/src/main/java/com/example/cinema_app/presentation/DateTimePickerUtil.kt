package com.example.cinema_app.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.presentation.view.shedule.ScheduleViewModel
import com.example.cinema_app.service.AlarmService
import com.google.android.material.snackbar.Snackbar
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

interface DateTimePickerUtil {
    fun clickButtonScheduleAlarm(
        fm: FragmentManager,
        cinema: Cinema,
        scheduleViewModel: ScheduleViewModel,
        alarmService: AlarmService,
        context: Context,
        view: View?
    ) {
        val calendar = Calendar.getInstance()
        val listenerDate = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectedDate =
                ZonedDateTime.ofInstant(calendar.toInstant(), calendar.timeZone.toZoneId())
                    .toLocalDate()
            val listenerTime = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val scheduleDate =
                    selectedDate.atTime(hourOfDay, minute).atZone(ZoneId.systemDefault())
                val alarmTime = scheduleDate.toInstant().toEpochMilli()
                val formatter =
                    DateTimeFormatter.ofPattern(context.getString(R.string.dateTimeFormat))

                scheduleViewModel.insertScheduleCinema(
                    ScheduleCinema(
                        cinema.originalId,
                        alarmTime.toString(),
                        scheduleDate.format(formatter).toString(),
                        cinema.originalId
                    )
                )

                alarmService.setExactAlarm(
                    cinema,
                    alarmTime,
                    cinema.originalId
                )

                val snackSheduleSave =
                    view?.let {
                        Snackbar.make(
                            it,
                            context.getString(R.string.scheduleSaveSnackbar),
                            Snackbar.LENGTH_LONG
                        )
                    }
                snackSheduleSave?.show()
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
