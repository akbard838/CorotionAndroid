package com.example.corotion.presentation

import android.content.Context
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corotion.R
import com.example.corotion.adapter.CountryAdapter
import com.example.corotion.data.ApiFactory
import com.example.corotion.data.CorotionApiClient
import com.example.corotion.data.response.SummaryResponse
import com.example.corotion.utils.AppConstant
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.emptyString
import com.example.corotion.utils.gone
import com.example.corotion.utils.hideKeyboard
import com.example.corotion.utils.onClick
import com.example.corotion.utils.setupToolbar
import com.example.corotion.utils.visible
import kotlinx.android.synthetic.main.activity_list_country.btnSearch
import kotlinx.android.synthetic.main.activity_list_country.edtSearch
import kotlinx.android.synthetic.main.activity_list_country.pbCountry
import kotlinx.android.synthetic.main.activity_list_country.rvCountry
import kotlinx.android.synthetic.main.activity_list_country.toolbarSearch
import kotlinx.android.synthetic.main.activity_list_country.tvError
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ListCountryActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<ListCountryActivity>(
            )
        }
    }

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var corotionApiClient: CorotionApiClient

    override val layoutResource: Int
        get() = R.layout.activity_list_country

    override fun onViewReady() {
        setupToolbar(toolbarSearch, isChild = true)

        countryAdapter = CountryAdapter(this)

        rvCountry.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            setHasFixedSize(true)
            adapter = countryAdapter
        }

        corotionApiClient = ApiFactory.createService(CorotionApiClient::class.java, this, AppConstant.BASE_URL)
        initCountryData()

        edtSearch.onEditorAction { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) initCountryData(edtSearch.text.toString().trim())
            hideKeyboard()
        }

        btnSearch.onClick {
            initCountryData(edtSearch.text.toString().trim())
            hideKeyboard()
        }
    }

    private fun initCountryData(query: String = emptyString()) {
        showLoading()
        corotionApiClient.getCoronaSummary().enqueue(object : Callback<SummaryResponse> {
            override fun onResponse(call: Call<SummaryResponse>, response: Response<SummaryResponse>) {
                if (response.isSuccessful) {
                    showSuccess()
                    response.body()?.countryItems?.let {
                        val mappedCountry = it.map { countryItem -> countryItem.toCountry() }

                        if (query.isEmpty()) {
                            countryAdapter.setData(mappedCountry)
                        } else {
                            val filteredCountry = mappedCountry.filter { countryItem ->
                                countryItem.country.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
                            }

                            countryAdapter.setData(filteredCountry)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showLoading() {
        tvError.gone()
        pbCountry.visible()
        rvCountry.gone()
    }

    private fun showError() {
        tvError.visible()
        pbCountry.gone()
    }

    private fun showSuccess() {
        rvCountry.visible()
        pbCountry.gone()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
