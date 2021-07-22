package com.example.nasajm.ui.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasajm.R
import com.example.nasajm.domain.ItemData
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity(R.layout.activity_recycler) {

    private var isNewList = false
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemDataList = arrayListOf(
            Pair(ItemData(1, "Mars", ""), false)
        )

        adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(itemData: ItemData) {
                    Toast.makeText(this@RecyclerActivity, itemData.someText, Toast.LENGTH_SHORT)
                        .show()
                }
            },
            object : RecyclerActivityAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            },
            itemDataList
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
            recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}