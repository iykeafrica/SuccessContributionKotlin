package com.example.successcontribution.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.successcontribution.R

class BackPressedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setTitle(R.string.back_pressed_dialog_title)
            it.setMessage(R.string.back_pressed_dialog_message)
            it.setPositiveButton(R.string.back_pressed_dialog_button_yes) { _, _ -> backPressed()}
            it.setNegativeButton(R.string.back_pressed_dialog_button_no) {_, _ -> dismiss()}
            it.create()
        }
    }

    interface Listener {
        fun onLogoutClicked()
    }

    private val listeners = HashSet<Listener>()

    private fun backPressed() {
        for (listener in listeners){
            listener.onLogoutClicked()
        }
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    companion object {
        fun newInstance(): BackPressedDialogFragment {
            return BackPressedDialogFragment()
        }
    }
}