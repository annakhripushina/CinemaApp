package com.example.cinema_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View


class CinemaAdapter(
    private val items: ArrayList<Cinema>,
    private val listener: CinemaClickListener
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CinemaViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            listener.onCinemaClick(
//                items[position],
//                holder.itemView,
//                position
//            )
//        }

        when (holder) {
            is CinemaViewHolder -> {
                holder.bind(items[position], listener)
            }}

    }

    override fun getItemCount(): Int = items.size

    interface CinemaClickListener {
        fun onCinemaClick(cinemaItem: Cinema, itemView: View, position: Int)
        fun onFavoriteClick(cinemaItem: Cinema, position: Int)
    }

}
