package com.example.successcontribution.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.successcontribution.R

class BackPressedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setCancelable(false)
            it.setMessage(R.string.back_pressed_dialog_message)
            it.setPositiveButton(R.string.back_pressed_dialog_button_yes) { _, _ -> activity?.finish() }
            it.setNegativeButton(R.string.back_pressed_dialog_button_no) { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(): BackPressedDialogFragment {
            return BackPressedDialogFragment()
        }
    }
}