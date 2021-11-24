package com.example.cinema_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener


class CinemaActivity : Fragment() {

    companion object {
        const val RESULT_ACTION = "action_user"
        const val RESULT_LIKE = "like"
        const val RESULT_COMMENT = "comment"
    }

    private lateinit var cinema: Cinema
    private var comment: String = ""
    private lateinit var input: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var button: Button
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var imageView: ImageView

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
            setFragmentResultListener(CinemaListActivity.EXTRA_CINEMA) { _, result ->
                cinema = result.getParcelable(CinemaListActivity.ITEM_CINEMA)!!
                populateViews(cinema)
                setClickListeners(cinema)

            }
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

        image?.let { imageView.setImageResource(it) }
        descriptionId?.let { description.setText(it) }
        title.setText(cinemaTitleId)
        checkBox.isChecked = cinema.like
    }

    private fun setClickListeners(cinema: Cinema) {

        button.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Привет! Приглашаю тебя посмотреть фильм \"${title.text}\"."
            )
            emailIntent.type = "text/plain"
            startActivity(Intent.createChooser(emailIntent, "Отправить приглашение через: "))
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            CinemaHolder.cinemaList[cinema.id].like = isChecked
            setFragmentResult(
                RESULT_ACTION,
                Bundle().apply {
                    putBoolean(RESULT_LIKE, isChecked)
                }
            )
        }

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                comment = input.getText().toString()
                setFragmentResult(
                    RESULT_ACTION,
                    Bundle().apply {
                        putString(RESULT_COMMENT, comment)
                    }
                )
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }

    private fun initViews(view: View) {
        input = view.findViewById(R.id.commentText)
        checkBox = view.findViewById(R.id.checkBoxLike)
        button = view.findViewById(R.id.buttonInvite)
        title = view.findViewById(R.id.titleDetail)
        description = view.findViewById(R.id.textDetail)
        imageView = view.findViewById(R.id.imageDetail)
    }

}