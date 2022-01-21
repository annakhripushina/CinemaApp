package com.example.cinema_app.presentation.view.shedule

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.SheduleCinema


class SheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.findViewById(R.id.titleView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val buttonEdit: TextView = itemView.findViewById(R.id.buttonEdit)
    private val buttonDelete: TextView = itemView.findViewById(R.id.buttonDelete)
    private val dateTimeAlarm: TextView = itemView.findViewById(R.id.dateTimeAlarm)

    @SuppressLint("ResourceAsColor")
    fun bind(item: Cinema, itemAlarm: SheduleCinema, listener: SheduleAdapter.SheduleClickListener) {
        titleView.text = item.title
        titleView.setTextColor(item.titleColor)
        dateTimeAlarm.text = itemAlarm.date_viewed

        Glide.with(imageView.context)
            .load(item.image)
            .centerCrop()
            .into(imageView)

        buttonDelete.setOnClickListener {
            listener.onDeleteClick(
                item,
                adapterPosition
            )
        }

        buttonEdit.setOnClickListener {
            listener.onCinemaClick(
                item,
                itemView,
                adapterPosition
            )
        }
    }
}