package com.example.cinema_app

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class CinemaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema)

        val idImage = intent.getIntExtra("extra_image", -1)
        val idDescription = intent.getIntExtra("extra_desc", -1)
        val idTitle = intent.getStringExtra("extra_title")
        var like = intent.getBooleanExtra("extra_like", false)

        findViewById<ImageView>(R.id.imageDetail).setImageResource(idImage)
        findViewById<TextView>(R.id.textDetail).setText(idDescription)
        findViewById<CheckBox>(R.id.checkBoxLike).isChecked = like

        findViewById<Button>(R.id.inviteFriend).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Привет! Приглашаю тебя посмотреть фильм \"$idTitle\".")
            emailIntent.type = "text/plain"
            startActivity(Intent.createChooser(emailIntent,
                "Send Email Using: "))
        }

        val intent = Intent()

        findViewById<CheckBox>(R.id.checkBoxLike).setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                like = true
                intent.putExtra(RESULT_LIKE, like)
                Log.d("LIKE", like.toString())
            }else{
                like = false
                intent.putExtra(RESULT_LIKE, like)
                Log.d("LIKE", like.toString())
            }
        }
        setResult(RESULT_CANCELED, intent)



    }
    companion object {
        const val RESULT_LIKE = "like"
    }
}