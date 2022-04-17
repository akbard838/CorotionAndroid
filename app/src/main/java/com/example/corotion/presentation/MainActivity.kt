package com.example.corotion.presentation

import android.content.Context
import com.example.corotion.R
import com.example.corotion.R.layout
import com.example.corotion.data.ApiFactory
import com.example.corotion.data.CorotionApiClient
import com.example.corotion.data.response.SummaryResponse
import com.example.corotion.utils.AppConstant
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.emptyString
import com.example.corotion.utils.gone
import com.example.corotion.utils.onClick
import com.example.corotion.utils.showCorotionDialog
import com.example.corotion.utils.showDialog
import com.example.corotion.utils.visible
import kotlinx.android.synthetic.main.activity_main.btnAbout
import kotlinx.android.synthetic.main.activity_main.btnInformation
import kotlinx.android.synthetic.main.activity_main.btnSearch
import kotlinx.android.synthetic.main.activity_main.pbConfirmed
import kotlinx.android.synthetic.main.activity_main.pbDeath
import kotlinx.android.synthetic.main.activity_main.pbRecovered
import kotlinx.android.synthetic.main.activity_main.tvDate
import kotlinx.android.synthetic.main.activity_main.tvErrorNewConfirmed
import kotlinx.android.synthetic.main.activity_main.tvErrorNewDeath
import kotlinx.android.synthetic.main.activity_main.tvErrorNewRecovered
import kotlinx.android.synthetic.main.activity_main.tvErrorTotalConfirmed
import kotlinx.android.synthetic.main.activity_main.tvErrorTotalDeath
import kotlinx.android.synthetic.main.activity_main.tvErrorTotalRecovered
import kotlinx.android.synthetic.main.activity_main.tvNewConfirmed
import kotlinx.android.synthetic.main.activity_main.tvNewDeath
import kotlinx.android.synthetic.main.activity_main.tvNewRecovered
import kotlinx.android.synthetic.main.activity_main.tvTotalConfirmed
import kotlinx.android.synthetic.main.activity_main.tvTotalDeath
import kotlinx.android.synthetic.main.activity_main.tvTotalRecovered
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                context.intentFor<MainActivity>().clearTask().newTask()
            )
        }
    }

    private lateinit var corotionApiClient: CorotionApiClient

    override val layoutResource: Int
        get() = layout.activity_main

    override fun onViewReady() {
        btnAbout.onClick {
            showCorotionDialog()
        }

        btnInformation.onClick {
            InformationActivity.start(this)
        }

        btnSearch.onClick {
            ListCountryActivity.start(this)
        }

        tvDate.text = String.format(getString(R.string.format_date), getStringCurrentDate())

        corotionApiClient = ApiFactory.createService(CorotionApiClient::class.java, this, AppConstant.BASE_URL)
        showSummaryData(true)
    }

    override fun onBackPressed() {
        showDialog {
            finish()
            exitProcess(0)
        }
    }

    override fun onResume() {
        super.onResume()
        if (tvNewConfirmed.text == emptyString()) showSummaryData(false)
    }

    private fun showSummaryData(isFirstTime: Boolean) {
        showLoading()
        corotionApiClient.getCoronaSummary().enqueue(object : Callback<SummaryResponse> {
            override fun onResponse(call: Call<SummaryResponse>, response: Response<SummaryResponse>) {
                if (response.isSuccessful) {
                    showSuccess()
                    with(response.body()?.globalItem) {
                        tvNewConfirmed.text = this?.newConfirmed.toString()
                        tvTotalConfirmed.text = this?.totalConfirmed.toString()
                        tvNewDeath.text = this?.newDeaths.toString()
                        tvTotalDeath.text = this?.totalDeaths.toString()
                        tvNewRecovered.text = this?.newRecovered.toString()
                        tvTotalRecovered.text = this?.totalRecovered.toString()
                    }
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                showErrorData()
                if (isFirstTime) {
                    showDialog (
                        "Aww, something error while loading data",
                        "retry",
                        "go offline"
                    ){
                        showSummaryData(true)
                    }
                }
            }
        })
    }

    private fun showErrorData(){
        tvErrorNewConfirmed.visible()
        tvErrorTotalConfirmed.visible()
        pbConfirmed.gone()

        tvErrorNewDeath.visible()
        tvErrorTotalDeath.visible()
        pbDeath.gone()

        tvErrorNewRecovered.visible()
        tvErrorTotalRecovered.visible()
        pbRecovered.gone()
    }

    private fun showLoading(){
        tvErrorNewConfirmed.gone()
        tvErrorTotalConfirmed.gone()
        pbConfirmed.visible()

        tvErrorNewDeath.gone()
        tvErrorTotalDeath.gone()
        pbDeath.visible()

        tvErrorNewRecovered.gone()
        tvErrorTotalRecovered.gone()
        pbRecovered.visible()
    }

    private fun showSuccess(){
        pbConfirmed.gone()
        pbDeath.gone()
        pbRecovered.gone()
    }

    private fun getStringCurrentDate(): String {
        val date = Date()
        val formatter = SimpleDateFormat("EEEE, MMMM d yyyy", Locale.US)
        return formatter.format(date)
    }
}
