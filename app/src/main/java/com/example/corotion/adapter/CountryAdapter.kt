package com.example.corotion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.corotion.R
import com.example.corotion.data.model.Country
import com.example.corotion.presentation.CountryDetailActivity
import com.example.corotion.utils.onClick
import kotlinx.android.synthetic.main.item_country.view.tvCountry

class CountryAdapter(
    val context: Context,
    val data: MutableList<Country> = mutableListOf()
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country) {
            with(itemView) {
                tvCountry.text = country.country

                itemView.onClick {
                    CountryDetailActivity.start(context, country)
                }

            }
        }
    }

    fun setData(list: List<Country>) {
        if (data.size > 0) {
            data.clear()
        }
        data.addAll(list)
        notifyDataSetChanged()
    }
}