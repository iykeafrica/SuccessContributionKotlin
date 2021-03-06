package com.example.successcontribution.screens.common.viewmvc

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.R

open class BaseViewMvc<LISTENER>(
    val activity: Activity
){
    private var progressDialog: ProgressDialog = ProgressDialog(activity, R.style.MyAlertDialogStyle)

    val listeners = HashSet<LISTENER>()

    fun registerListener(listener: LISTENER){
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER) {
        listeners.remove(listener)
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showProgressIndication() {
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();
    }

    fun hideProgressIndication() {
        progressDialog.dismiss()
    }

    fun hideOpeningKeyBoard(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setOnClickListener {
            editText.inputType = InputType.TYPE_CLASS_TEXT
            editText.requestFocus()
            val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        }
    }


}