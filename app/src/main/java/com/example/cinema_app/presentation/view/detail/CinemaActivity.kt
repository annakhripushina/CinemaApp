package com.example.cinema_app.presentation.view.detail

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
import androidx.lifecycle.Observer
import com.example.cinema_app.service.AlarmService
import com.bumptech.glide.Glide
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.DateTimePickerUtil
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.example.cinema_app.presentation.viewmodel.CinemaViewModelFactory


class CinemaActivity : Fragment(), DateTimePickerUtil {
    private val viewModel: CinemaViewModel by activityViewModels {
        CinemaViewModelFactory(
            requireActivity().application
        )
    }
    private lateinit var cinema: Cinema
    private lateinit var input: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var buttonInvite: ImageView
    private lateinit var buttonReminder: ImageView
    private lateinit var description: TextView
    private lateinit var imageView: ImageView
    private lateinit var titleToolbar: Toolbar
    private lateinit var alarmService: AlarmService

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
        initAlarmService()

        cinema = viewModel.cinemaItem
        populateViews(cinema)
        setClickListeners(cinema)

        viewModel.allLikedCinema.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                viewModel.getLike(cinema)
                checkBox.isChecked = viewModel.hasLiked
            }
        })
    }

    private fun initAlarmService() {
        alarmService = AlarmService(requireContext())
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
    }

    private fun setClickListeners(cinema: Cinema) {
        buttonInvite.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Привет! Приглашаю тебя посмотреть фильм \"${titleToolbar.title}\"."
            )
            emailIntent.type = "text/plain"
            startActivity(Intent.createChooser(emailIntent, "Отправить приглашение через: "))
        }

        buttonReminder.setOnClickListener{
            clickScheduleMovieAlarm(
                requireActivity().supportFragmentManager,
                cinema,
                viewModel,
                alarmService
            )
            /*val calendar = Calendar.getInstance()
            val listenerDate = DatePickerDialog.OnDateSetListener { view, year,
                                                                month, dayOfMonth ->
                val listenerTime =
                    TimePickerDialog.OnTimeSetListener { listeningView, hourOfDay, minute ->
                        //Do something
                    }

                TimePickerDialog(
                    requireContext(), listenerTime,
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE],
                    true
                ).show()
            }
            DatePickerDialog(
                requireContext(),
                listenerDate,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
*/

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
        buttonInvite = view.findViewById(R.id.imageInvite)
        buttonReminder = view.findViewById(R.id.imageReminder)
        titleToolbar = view.findViewById(R.id.toolbar)
        description = view.findViewById(R.id.textDetail)
        imageView = view.findViewById(R.id.imageDetail)
    }
}