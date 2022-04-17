package com.example.corotion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corotion.R
import com.example.corotion.adapter.InformationAdapter.ViewHolder
import com.example.corotion.data.model.Information
import com.example.corotion.utils.emptyString
import com.example.corotion.utils.enum.InformationViewType
import com.example.corotion.utils.gone
import kotlinx.android.synthetic.main.item_information_grid.view.clInformation
import kotlinx.android.synthetic.main.item_information_grid.view.imgInformation
import kotlinx.android.synthetic.main.item_information_grid.view.tvInformation
import kotlinx.android.synthetic.main.item_information_linear.view.imgInformationLinear
import kotlinx.android.synthetic.main.item_information_linear.view.tvDescription
import kotlinx.android.synthetic.main.item_information_linear.view.tvTitle

class InformationAdapter(
    val context: Context,
    val data: MutableList<Information> = mutableListOf(),
    val type: String = emptyString(),
    val viewType: Int = InformationViewType.GRID.type
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == InformationViewType.GRID.type) GridInformationViewHolder(
            inflater.inflate(
                R.layout.item_information_grid,
                parent,
                false
            )
        )
        else LinearInformationViewHolder(
            inflater.inflate(
                R.layout.item_information_linear,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (viewType == InformationViewType.GRID.type) {
            val viewHolder = holder as GridInformationViewHolder
            viewHolder.bind(data[position])
        } else {
            val viewHolder = holder as LinearInformationViewHolder
            viewHolder.bind(data[position])
        }
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class GridInformationViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(information: Information) {
            with(itemView) {
                if (type == "wash") {
                    imgInformation.layoutParams.height = 250
                    imgInformation.layoutParams.width = 220
                    clInformation.layoutParams.height = 500
                }
                tvInformation.text = information.description

                Glide.with(context)
                    .load(information.image)
                    .into(imgInformation)
            }
        }
    }

    inner class LinearInformationViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(information: Information) {
            with(itemView) {

                tvTitle.maxLines = if (information.image != 0) 1 else Int.MAX_VALUE
                tvTitle.text = information.title
                tvDescription.text = information.description

                if (information.image != 0) {
                    Glide.with(context)
                        .load(information.image)
                        .into(imgInformationLinear)
                } else {
                    imgInformationLinear.gone()
                }
            }
        }
    }

    fun setData(list: List<Information>) {
        if (data.size > 0) {
            data.clear()
        }
        data.addAll(list)
        notifyDataSetChanged()
    }
}