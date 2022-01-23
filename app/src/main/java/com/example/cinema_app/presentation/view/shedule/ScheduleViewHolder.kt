package com.example.cinema_app.presentation.view.shedule

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema


class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.findViewById(R.id.titleView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val buttonEdit: ImageView = itemView.findViewById(R.id.imageEdit)
    private val buttonDelete: ImageView = itemView.findViewById(R.id.imageDelete)
    private val dateView: TextView = itemView.findViewById(R.id.dateAlarm)

    @SuppressLint("ResourceAsColor")
    fun bind(item: Cinema, listener: ScheduleAdapter.ScheduleClickListener) {
        titleView.text = item.title
        titleView.setTextColor(item.titleColor)
        dateView.text = item.dateViewed

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
            listener.onEditClick(
                item,
                itemView,
                adapterPosition
            )
        }
    }
}