package com.example.cinema_app

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DialogBack: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_back)
            .setNegativeButton("Нет") { dialog, which -> }//???
            .setPositiveButton("Да") { dialog, which -> (activity as MainActivity).superOnBackPressed()} //??? finish() / super.onBackPressed()
            .create()
    }

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogView = inflater!!.inflate(R.layout.dialog_back, container, false)
        return inflater.inflate(R.layout.dialog_back, container, false)
    }
*/

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