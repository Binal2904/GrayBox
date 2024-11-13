package com.example.grayboxpractical

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grayboxpractical.databinding.ItemGridBinding

class GridViewAdapter(
    private val items: List<Item>, private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<GridViewAdapter.NumberViewHolder>() {

    inner class NumberViewHolder(private val binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            val color = when (item.type) {
                CellType.BLUE -> Color.BLUE
                CellType.RED -> Color.RED
                CellType.GRAYED -> Color.GRAY
                else -> Color.WHITE
            }
            binding.viewChange.setBackgroundColor(color)
            binding.root.setOnClickListener { itemClick(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}