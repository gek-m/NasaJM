package com.example.nasajm.ui.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nasajm.domain.ItemData

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: Pair<ItemData, Boolean>)
}