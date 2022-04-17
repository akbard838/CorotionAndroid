package com.example.corotion.presentation

import android.content.Context
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corotion.R
import com.example.corotion.adapter.InformationAdapter
import com.example.corotion.data.model.Information
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.enum.InformationViewType
import com.example.corotion.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_preventions.rvPreventions
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.jetbrains.anko.startActivity

class PreventionsActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<PreventionsActivity>(
            )
        }
    }

    private val informationAdapter by lazy {
        InformationAdapter(this, viewType = InformationViewType.LINEAR.type)
    }

    override val layoutResource: Int
        get() = R.layout.activity_preventions

    override fun onViewReady() {
        setupToolbar(toolbar, isChild = true)

        rvPreventions.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = informationAdapter
        }

        informationAdapter.setData(getPreventionsData())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getPreventionsData(): MutableList<Information> {
        val informations = mutableListOf<Information>()

        val images = resources.obtainTypedArray(R.array.list_of_preventions_image)
        val titles = resources.getStringArray(R.array.list_of_preventions_title)
        val descriptions = resources.getStringArray(R.array.list_of_preventions_description)

        descriptions.forEachIndexed { i, _ ->
            informations.add(
                Information(
                    images.getResourceId(i, 0),
                    title = titles[i],
                    description = descriptions[i]
                )
            )
        }

        images.recycle()

        return informations
    }
}
