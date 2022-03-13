package com.example.cinema_app.presentation.view.cinemaList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema


class CinemaAdapter(
    private val listener: CinemaClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Cinema>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(cinemaList: ArrayList<Cinema>) {
        items.clear()
        items.addAll(cinemaList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CinemaViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CinemaViewHolder -> {
                holder.bind(items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    interface CinemaClickListener {
        fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int)
        fun onLongCinemaClick(cinemaItem: Cinema, itemView: View, position: Int)
    }

}
