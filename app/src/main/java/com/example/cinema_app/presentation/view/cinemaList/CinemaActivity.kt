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
import androidx.fragment.app.setFragmentResult
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.CinemaHolder
import com.example.cinema_app.R
import com.example.cinema_app.presentation.viewmodel.CinemaListViewModel


class CinemaActivity : Fragment() {
    companion object {
        const val RESULT_ACTION = "action_user"
        const val RESULT_LIKE = "like"
        const val RESULT_COMMENT = "comment"
    }

    private val viewModel: CinemaListViewModel by activityViewModels()
    private lateinit var cinema: Cinema
    private var comment: String = ""
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

        if (savedInstanceState == null)
            /*setFragmentResultListener(CinemaListActivity.EXTRA_CINEMA) { _, result ->
                cinema = result.getParcelable(CinemaListActivity.ITEM_CINEMA)!!
                populateViews(cinema)
                setClickListeners(cinema)
            }*/
        { cinema = viewModel.cinemaItem
                populateViews(cinema)
                setClickListeners(cinema)}
        else {
            cinema = savedInstanceState.getParcelable("CINEMA_LIST")!!
            populateViews(cinema)
            setClickListeners(cinema)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("CINEMA_LIST", cinema)
    }

    private fun populateViews(cinema: Cinema) {
        val image = cinema.image
        val descriptionId = cinema.description
        val cinemaTitleId = cinema.title

        image.let { imageView.setImageResource(it) }
        descriptionId.let { description.setText(it) }
        titleToolbar.title = getText(cinemaTitleId)
        checkBox.isChecked = cinema.hasLiked
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
            /*CinemaHolder.cinemaList[cinema.id].hasLiked = isChecked
            setFragmentResult(
                RESULT_ACTION,
                Bundle().apply {
                    putBoolean(RESULT_LIKE, isChecked)
                }
            )*/
            viewModel.onSetLikeClickListener(cinema.id, isChecked)
        }

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                /*comment = input.text.toString()
                setFragmentResult(
                    RESULT_ACTION,
                    Bundle().apply {
                        putString(RESULT_COMMENT, comment)
                    }
                )*/
                viewModel.onTextChangedListener(input)
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