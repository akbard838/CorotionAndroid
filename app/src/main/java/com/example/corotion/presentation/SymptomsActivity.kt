package com.example.corotion.presentation

import android.content.Context
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.corotion.R
import com.example.corotion.adapter.InformationAdapter
import com.example.corotion.data.model.Information
import com.example.corotion.utils.BaseActivity
import com.example.corotion.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_symptoms.rvSymptoms
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import org.jetbrains.anko.startActivity

class SymptomsActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<SymptomsActivity>(
            )
        }
    }

    private lateinit var mInformationAdapter: InformationAdapter
    private var informations: MutableList<Information> = mutableListOf()

    override val layoutResource: Int
        get() = R.layout.activity_symptoms

    override fun onViewReady() {
        setupToolbar(toolbar, isChild = true)

        mInformationAdapter = InformationAdapter(this, type = "symptoms")

        rvSymptoms.apply {
            layoutManager =  GridLayoutManager(this@SymptomsActivity, 2)
            setHasFixedSize(true)
            adapter = mInformationAdapter
        }

        initSymptomsData()
    }

    private fun initSymptomsData() {
        val images = resources.obtainTypedArray(R.array.list_of_symptoms_image)
        val descriptions = resources.getStringArray(R.array.list_of_symptoms_information)

        descriptions.forEachIndexed { i, _ ->
            informations.add(
                Information(
                    images.getResourceId(i, 0),
                    descriptions[i]
                )
            )
        }

        mInformationAdapter.setData(informations)
        images.recycle()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
