package com.example.cinema_app

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CinemaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView : TextView = itemView.findViewById(R.id.titleView)
    private val imageView : ImageView = itemView.findViewById(R.id.imageView)
    private val buttonDetail : TextView = itemView.findViewById(R.id.buttonDetail)

    fun bind(item: Cinema, listener: CinemaAdapter.CinemaClickListener){
        titleView.setText(item.title)
        imageView.setImageResource(item.image)
        //titleView.setTextColor(item.titleColor)

        buttonDetail.setOnClickListener {
            listener.onCinemaClick(
                item,
                itemView,
                adapterPosition
            )
        }
    }

}