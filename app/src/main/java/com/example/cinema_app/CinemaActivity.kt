package com.example.cinema_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class CinemaActivity : AppCompatActivity() {
    private lateinit var input : EditText
    private var comment: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema)

        input = findViewById(R.id.commentText)
        val cinema = intent.getParcelableExtra<Cinema>("extra_cinema")!!
        val image = cinema.image
        val description = cinema.description
        val title = cinema.title
        var like = cinema.like
        var intent = Intent()

        image?.let { findViewById<ImageView>(R.id.imageDetail).setImageResource(it) }
        description?.let { findViewById<TextView>(R.id.textDetail).setText(it) }
        title.let { findViewById<TextView>(R.id.titleDetail).setText(it) }
        findViewById<CheckBox>(R.id.checkBoxLike).isChecked = like

        findViewById<Button>(R.id.inviteFriend).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Привет! Приглашаю тебя посмотреть фильм \"$title\".")
            emailIntent.type = "text/plain"
            startActivity(Intent.createChooser(emailIntent,
                "Отправить приглашение через: "))
        }

        findViewById<CheckBox>(R.id.checkBoxLike).setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                like = true
                intent.putExtra(RESULT_LIKE, like)
            }else{
                like = false
                intent.putExtra(RESULT_LIKE, like)
            }
            when (cinema.id){
                1 -> CinemaHolder.cinema1.like = like
                2 -> CinemaHolder.cinema2.like = like
                3 -> CinemaHolder.cinema3.like = like
            }
        }

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                comment = input.getText().toString()
                intent.putExtra(RESULT_COMMENT, comment)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        setResult(RESULT_FIRST_USER, intent)

    }

    companion object {
        const val RESULT_LIKE = "like"
        const val RESULT_COMMENT = "comment"
    }
}