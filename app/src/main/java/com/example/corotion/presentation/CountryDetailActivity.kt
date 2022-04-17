package com.example.corotion.presentation

import android.content.Context
import android.view.MenuItem
import com.example.corotion.R
import com.example.corotion.data.model.Country
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.BundleKeys
import com.example.corotion.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_country_detail.tvCountry
import kotlinx.android.synthetic.main.activity_country_detail.tvNewConfirmed
import kotlinx.android.synthetic.main.activity_country_detail.tvNewDeath
import kotlinx.android.synthetic.main.activity_country_detail.tvNewRecovered
import kotlinx.android.synthetic.main.activity_country_detail.tvTotalConfirmed
import kotlinx.android.synthetic.main.activity_country_detail.tvTotalDeath
import kotlinx.android.synthetic.main.activity_country_detail.tvTotalRecovered
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.jetbrains.anko.startActivity

class CountryDetailActivity : BaseActivity() {

    companion object {
        fun start(context: Context, country: Country) {
            context.startActivity<CountryDetailActivity>(
                BundleKeys.KEY_COUNTRY to country
            )
        }
    }

    private var country: Country? = null

    override val layoutResource: Int
        get() = R.layout.activity_country_detail

    override fun onViewReady() {
        setupToolbar(toolbar, isChild = true)
        country = intent.getParcelableExtra(BundleKeys.KEY_COUNTRY)


        tvCountry.text = country?.country
        tvNewConfirmed.text = country?.newConfirmed.toString()
        tvTotalConfirmed.text = country?.totalConfirmed.toString()
        tvNewDeath.text = country?.newDeaths.toString()
        tvTotalDeath.text = country?.totalDeaths.toString()
        tvNewRecovered.text = country?.newRecovered.toString()
        tvTotalRecovered.text = country?.totalRecovered.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
