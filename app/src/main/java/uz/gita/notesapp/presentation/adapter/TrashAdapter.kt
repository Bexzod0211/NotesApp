package uz.gita.notesapp.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat


class TrashAdapter() : ListAdapter<NoteData,TrashAdapter.Holder>(MyDiffUtil) {
    private lateinit var onLongItemClickListener:(NoteData)->Unit
    object MyDiffUtil : DiffUtil.ItemCallback<NoteData>(){
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.title == newItem.title
                    &&oldItem.content == newItem.content
                    &&oldItem.date == newItem.date
                    &&oldItem.pinned == newItem.pinned
        }

    }

    inner class Holder(private val binding:ItemNoteBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.txtContent.setOnLongClickListener {
                onLongItemClickListener.invoke(getItem(adapterPosition))
                true
            }
        }

        fun bind(position:Int){
            binding.apply {
                val data = getItem(position)
                data.apply {
                    txtContent.text = content.parseAsHtml()
                    txtContent.isFocusable = false
                    txtTitle.text = title
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val timeFormat = SimpleDateFormat("HH:mm:ss")
                    val dateString:String =dateFormat.format(date)
                    val timeString:String = timeFormat.format(date)
                    txtDate.text = "$dateString\n$timeString"
                }
            }

        }
    }

    fun setOnLongItemClickListener(block:(NoteData)->Unit){
        onLongItemClickListener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }
}