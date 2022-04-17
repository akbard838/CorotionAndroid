package com.example.corotion.presentation

import android.content.Context
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corotion.R
import com.example.corotion.adapter.InformationAdapter
import com.example.corotion.data.model.Information
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.enum.InformationViewType.LINEAR
import com.example.corotion.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_about_covid.rvAbout
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.jetbrains.anko.startActivity

class AboutCovidActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<AboutCovidActivity>(
            )
        }
    }

    private val informationAdapter by lazy {
        InformationAdapter(this, viewType = LINEAR.type)
    }

    override val layoutResource: Int
        get() = R.layout.activity_about_covid

    override fun onViewReady() {
        setupToolbar(toolbar, isChild = true)

        rvAbout.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = informationAdapter
        }

        informationAdapter.setData(getAboutData())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAboutData(): MutableList<Information> {
        val informations = mutableListOf<Information>()

        val titles = resources.getStringArray(R.array.list_of_about_title)
        val descriptions = resources.getStringArray(R.array.list_of_about_description)

        descriptions.forEachIndexed { i, _ ->
            informations.add(
                Information(
                    title = titles[i],
                    description = descriptions[i]
                )
            )
        }

        return informations
    }
}
