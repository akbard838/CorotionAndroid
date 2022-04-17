package com.example.corotion.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.corotion.R
import kotlinx.android.synthetic.main.layout_dialog_corotion.view.btnClose
import kotlinx.android.synthetic.main.layout_dialog_without_icon.view.btnNegative
import kotlinx.android.synthetic.main.layout_dialog_without_icon.view.btnPositive
import kotlinx.android.synthetic.main.layout_dialog_without_icon.view.tvMessage

fun Context.showCorotionDialog() {
    val view: View = LayoutInflater.from(this).inflate(R.layout.layout_dialog_corotion, null)

    val builder = AlertDialog.Builder(this).setView(view)
    val dialog = builder.create()

    dialog.apply {
        window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.CENTER)
            val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 60)
            setBackgroundDrawable(inset)
        }
        setCancelable(false)
        show()
    }

    with(view) {
        btnClose.onClick {
            dialog.dismiss()
        }
    }
}

fun Context.showDialog(
    message: String? = null,
    textPositive: String? = null,
    textNegative: String? = null,
    positiveListener: (() -> Unit)? = null
) {
    val view: View = LayoutInflater.from(this).inflate(R.layout.layout_dialog_without_icon, null)

    if (message != null) view.tvMessage.text = message
    if (textNegative != null) view.btnNegative.text = textNegative
    if (textPositive != null) view.btnPositive.text = textPositive


    val builder = AlertDialog.Builder(this).setView(view)
    val dialog = builder.create()

    dialog.apply {
        window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.CENTER)
            val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 60)
            setBackgroundDrawable(inset)
        }
        setCancelable(true)
        show()
    }

    with(view) {
        btnPositive.onClick {
            dialog.dismiss()
            positiveListener?.invoke()
        }
        btnNegative.onClick {
            dialog.dismiss()
        }
    }
}