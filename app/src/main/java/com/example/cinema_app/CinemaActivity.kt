package com.example.cinema_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class CinemaActivity : AppCompatActivity() {

    private var comment: String = ""
    private lateinit var input: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var button: Button
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema)

        val cinema = intent.getParcelableExtra<Cinema>("extra_cinema")!!
        initViews()
        populateViews(cinema)
        setClickListeners(cinema)

        setResult(RESULT_FIRST_USER, intent)
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
        var like: Boolean
        button.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Привет! Приглашаю тебя посмотреть фильм \"${title.text}\".")
            emailIntent.type = "text/plain"
            startActivity(Intent.createChooser(emailIntent, "Отправить приглашение через: "))
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            like = isChecked
            CinemaHolder.cinemaList[cinema.id].like = like
            //cinema.like = like
            intent.putExtra(RESULT_LIKE, like)
        }

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                comment = input.getText().toString()
                intent.putExtra(RESULT_COMMENT, comment)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initViews() {
        input = findViewById(R.id.commentText)
        checkBox = findViewById(R.id.checkBoxLike)
        button = findViewById(R.id.buttonInvite)
        title = findViewById(R.id.titleDetail)
        description = findViewById(R.id.textDetail)
        imageView = findViewById(R.id.imageDetail)
    }

    companion object {
        const val RESULT_LIKE = "like"
        const val RESULT_COMMENT = "comment"
    }
}