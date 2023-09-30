package uz.gita.notesapp.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.notesapp.data.model.ColorData
import uz.gita.notesapp.databinding.ItemColorBinding

class ColorAdapter(private val resources: Resources) : ListAdapter<ColorData, ColorAdapter.Holder>(MyDiffUtil) {
    private var selectedColorPos = -1
    private lateinit var onItemClickListener: (Int) -> Unit

    object MyDiffUtil : DiffUtil.ItemCallback<ColorData>() {
        override fun areItemsTheSame(oldItem: ColorData, newItem: ColorData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ColorData, newItem: ColorData): Boolean {
            return oldItem.colorResId == newItem.colorResId
        }

    }

    inner class Holder(private val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                notifyItemChanged(selectedColorPos)
                selectedColorPos = adapterPosition
                notifyItemChanged(selectedColorPos)
                onItemClickListener.invoke(getItem(selectedColorPos).colorResId)
            }
        }

        fun bind(position: Int) {
            val data = getItem(position)
            binding.cardView.setCardBackgroundColor(resources.getColor(data.colorResId))
            if (selectedColorPos == position) {
                binding.imgSelect.visibility = View.VISIBLE
            } else {
                binding.imgSelect.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }


    fun setOnItemClickListener(block: (Int) -> Unit) {
        onItemClickListener = block
    }


}

