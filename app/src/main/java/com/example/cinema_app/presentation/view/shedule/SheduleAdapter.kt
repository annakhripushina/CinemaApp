package com.example.cinema_app.presentation.view.shedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema


class SheduleAdapter(
    private val listener: SheduleClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Cinema>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(favoriteList: ArrayList<Cinema>) {
        items.clear()
        items.addAll(favoriteList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SheduleViewHolder(inflater.inflate(R.layout.shedule_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SheduleViewHolder -> {
                holder.bind(items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    interface SheduleClickListener {
        fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int)
        fun onDeleteClick(cinemaItem: Cinema, position: Int)
    }
}
