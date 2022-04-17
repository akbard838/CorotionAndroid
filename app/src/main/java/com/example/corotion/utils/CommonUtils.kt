package com.example.corotion.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun emptyString(): String {
    return ""
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.onClick(listener: () -> Unit) {
    this.setOnClickListener {
        listener.invoke()
    }
}

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar?,
    title: String = emptyString(),
    isChild: Boolean = false
) {
    toolbar?.let {
        setSupportActionBar(it)

        if (!isChild) {
            it.navigationIcon = null
        }
    }

    if (supportActionBar != null) {
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(isChild)
    }
}

fun Activity.hideKeyboard() {
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}