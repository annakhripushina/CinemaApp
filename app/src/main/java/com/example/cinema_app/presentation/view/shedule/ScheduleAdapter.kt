package com.example.cinema_app.presentation.view.shedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema


class ScheduleAdapter(
    private val listener: ScheduleClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Cinema>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(ScheduleList: ArrayList<Cinema>) {
        items.clear()
        items.addAll(ScheduleList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleViewHolder(inflater.inflate(R.layout.schedule_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScheduleViewHolder -> {
                holder.bind(items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    interface ScheduleClickListener {
        fun onEditClick(cinemaItem: Cinema, itemView: View, position: Int)
        fun onDeleteClick(cinemaItem: Cinema, position: Int)
    }
}
