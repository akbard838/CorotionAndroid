package com.example.corotion.presentation

import android.content.Context
import android.view.MenuItem
import com.example.corotion.R
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.onClick
import com.example.corotion.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_information.btnCovid
import kotlinx.android.synthetic.main.activity_information.btnPreventions
import kotlinx.android.synthetic.main.activity_information.btnSymptoms
import kotlinx.android.synthetic.main.activity_information.btnWashingHand
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.jetbrains.anko.startActivity

class InformationActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<InformationActivity>(
            )
        }
    }

    override val layoutResource: Int
        get() = R.layout.activity_information

    override fun onViewReady() {
        setupToolbar(toolbar, isChild = true)

        btnCovid.onClick {
            AboutCovidActivity.start(this)
        }
        btnSymptoms.onClick {
            SymptomsActivity.start(this)
        }
        btnWashingHand.onClick {
            WashingHandActivity.start(this)
        }
        btnPreventions.onClick {
            PreventionsActivity.start(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
