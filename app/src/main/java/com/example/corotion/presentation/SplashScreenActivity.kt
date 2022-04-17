package com.example.corotion.presentation

import com.example.corotion.R.layout
import com.example.corotion.utils.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : BaseActivity() {

    override val layoutResource: Int
        get() = layout.activity_splash_screen

    override fun onViewReady() {
        GlobalScope.launch {
            delay(2000)
            toNextActivity()
        }
    }

    private fun toNextActivity() {
        MainActivity.start(this@SplashScreenActivity)
        finish()
    }
}
