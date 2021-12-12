package com.example.cinema_app.presentation.view.cinemaList

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.viewmodel.CinemaListViewModel


class CinemaActivity : Fragment() {
    private val viewModel: CinemaListViewModel by activityViewModels()

    private lateinit var cinema: Cinema
    private lateinit var input: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var button: Button
    private lateinit var description: TextView
    private lateinit var imageView: ImageView
    private lateinit var titleToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.activity_cinema,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        cinema = viewModel.cinemaItem
        populateViews(cinema)
        setClickListeners(cinema)

    }

    private fun populateViews(cinema: Cinema) {
        val descriptionId = cinema.description
        val cinemaTitleId = cinema.title

        Glide.with(imageView.context)
            .load(cinema.image)
            .centerCrop()
            .into(imageView)

        descriptionId.let { description.text = it }
        titleToolbar.title = cinemaTitleId
        viewModel.getLike(cinema)
        checkBox.isChecked = viewModel.hasLiked
    //viewModel.hasLiked(cinema)
        //checkBox.isChecked = viewModel.hasLiked//cinema.hasLiked
    }

    private fun setClickListeners(cinema: Cinema) {
        button.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Привет! Приглашаю тебя посмотреть фильм \"${titleToolbar.title}\"."
            )
            emailIntent.type = "text/plain"
            startActivity(Intent.createChooser(emailIntent, "Отправить приглашение через: "))
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSetLikeClickListener(cinema, isChecked)
        }

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.onSetComment(input)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initViews(view: View) {
        input = view.findViewById(R.id.commentText)
        checkBox = view.findViewById(R.id.checkBoxLike)
        button = view.findViewById(R.id.buttonInvite)
        titleToolbar = view.findViewById(R.id.toolbar)
        description = view.findViewById(R.id.textDetail)
        imageView = view.findViewById(R.id.imageDetail)

    }
}