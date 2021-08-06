package com.example.successcontribution.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.successcontribution.R
import com.example.successcontribution.screens.login.LoginActivity

class BackPressedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setCancelable(false)
            it.setMessage(R.string.back_pressed_dialog_message)
            it.setPositiveButton(R.string.back_pressed_dialog_button_yes) { _, _ -> logout()}
            it.setNegativeButton(R.string.back_pressed_dialog_button_no) { _, _ -> dismiss() }
            it.create()
        }
    }

    private fun logout() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        fun newInstance(): BackPressedDialogFragment {
            return BackPressedDialogFragment()
        }
    }
}