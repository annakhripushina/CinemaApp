package com.example.cinema_app

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment


class DialogBack: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_back)
            .setNegativeButton("Нет") { dialog, which -> }//???
            .setPositiveButton("Да") { dialog, which -> (activity as MainActivity).superOnBackPressed()} //??? finish() / super.onBackPressed()
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG_Create", "CREATE")
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCancel(dialog: DialogInterface) {
        Log.d("TAG_Cancel", "CANCEL")
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        Log.d("TAG_Dismiss", "DISMISS")
        super.onDismiss(dialog)
    }

}