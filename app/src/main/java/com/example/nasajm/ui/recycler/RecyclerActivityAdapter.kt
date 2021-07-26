package com.example.nasajm.ui.recycler

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nasajm.R
import com.example.nasajm.domain.ItemData
import kotlinx.android.synthetic.main.activity_recycle_item.view.*

class RecyclerActivityAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private val dragListener: OnStartDragListener,
    private var itemData: MutableList<Pair<ItemData, Boolean>>
) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MARS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_MARS ->
                MarsViewHolder(
                    inflater.inflate(R.layout.activity_recycle_item, parent, false) as View
                )
            else ->
                HeaderViewHolder(
                    inflater.inflate(R.layout.activity_recycle_item_header, parent, false) as View
                )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(itemData[position])
    }

    override fun getItemCount(): Int = itemData.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            else -> TYPE_MARS
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        itemData.removeAt(fromPosition).apply {
            itemData.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemChanged(fromPosition)
        notifyItemChanged(toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        itemData.removeAt(position)
        if (position == itemCount) notifyItemChanged(position - 1)
        if (position == 1) notifyItemChanged(position + 1)
        notifyItemRemoved(position)
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(dataItem: Pair<ItemData, Boolean>) {
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(dataItem.first)
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        override fun bind(dataItem: Pair<ItemData, Boolean>) {
            itemView.marsImageView.setOnClickListener { onListItemClickListener.onItemClick(dataItem.first) }
            itemView.addItemImageView.setOnClickListener { addItem() }
            itemView.removeItemImageView.setOnClickListener { removeItem() }

            itemView.moveItemDown.apply {
                setOnClickListener { moveDown() }
                visibility = if (layoutPosition == itemCount - 1) View.INVISIBLE else View.VISIBLE
            }
            itemView.moveItemUp.apply {
                setOnClickListener { moveUp() }
                visibility = if (layoutPosition > 1) View.VISIBLE else View.INVISIBLE
            }

            val spannable = SpannableStringBuilder(itemView.marsDescriptionTextView.text)
            spannable.setSpan(ForegroundColorSpan(Color.RED), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            itemView.marsDescriptionTextView.setText(spannable, TextView.BufferType.SPANNABLE)

            itemView.marsDescriptionTextView.visibility =
                if (dataItem.second) View.VISIBLE else View.GONE

            itemView.marsTextView.setOnClickListener { toggleText() }
            itemView.dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }

        private fun addItem() {
            itemData.add(layoutPosition, generateItem())
            notifyItemChanged(layoutPosition)
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            itemData.removeAt(layoutPosition)
            notifyItemChanged(layoutPosition - 1)
            notifyItemChanged(layoutPosition + 1)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                itemData.removeAt(currentPosition).apply {
                    itemData.add(currentPosition - 1, this)
                }
                notifyItemChanged(currentPosition)
                notifyItemChanged(currentPosition - 1)
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < itemData.size - 1 }?.also { currentPosition ->
                itemData.removeAt(currentPosition).apply {
                    itemData.add(currentPosition + 1, this)
                }
                notifyItemChanged(currentPosition)
                notifyItemChanged(currentPosition + 1)
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun toggleText() {
            itemData[layoutPosition] = itemData[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    fun appendItem() {
        itemData.add(generateItem())
        notifyItemChanged(itemCount - 2)
        notifyItemInserted(itemCount - 1)
    }

    interface OnListItemClickListener {
        fun onItemClick(itemData: ItemData)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    private fun generateItem() = Pair(ItemData(1, "Mars", ""), false)
}