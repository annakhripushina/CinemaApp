package com.example.cinema_app.presentation.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cinema_app.R


class DialogBack : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_back)
            .setNegativeButton(getString(R.string.negativeButton)) { _, _ -> }
            .setPositiveButton(getString(R.string.positiveButton)) { _, _ -> (activity as MainActivity).finish() }
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }
}