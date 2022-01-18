package com.example.cinema_app.presentation

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentManager
import com.example.cinema_app.service.AlarmService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.WatchCinema
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

interface DateTimePickerUtil {
    @SuppressLint("NewApi")
    fun clickScheduleMovieAlarm(
        fm: FragmentManager,
        cinema: Cinema,
        cinemaViewModel: CinemaViewModel,
        alarmService: AlarmService
    ) {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.show(fm, picker.toString())

        picker.addOnPositiveButtonClickListener {
            val localDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(picker.selection!!.toLong()),
                ZoneOffset.UTC
            ).toLocalDate()

            val materialTimePicker = MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            materialTimePicker.show(fm, materialTimePicker.toString())

            materialTimePicker.addOnPositiveButtonClickListener {
                val newHour = materialTimePicker.hour
                val newMinute = materialTimePicker.minute
                val scheduleDateTime =
                    localDateTime.atTime(newHour, newMinute).atZone(ZoneId.systemDefault())
                val requestCode = cinema.original_id
                val alarmTime = scheduleDateTime.toInstant().toEpochMilli()

                cinemaViewModel.insertWatchCinema(WatchCinema(cinema.original_id, alarmTime.toString(), requestCode))
                /*movieViewModel.updateScheduleTime(movie.uniqueId, scheduleDateTime.toString())
                movieViewModel.saveScheduleInfo(movie.title, requestCode, alarmTime)*/
                alarmService.setExactAlarm(
                    cinema,
                    alarmTime,
                    requestCode
                )
            }
        }
    }
}
