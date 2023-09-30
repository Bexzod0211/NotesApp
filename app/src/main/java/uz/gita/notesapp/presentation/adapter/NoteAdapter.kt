package uz.gita.notesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat


class NoteAdapter() : ListAdapter<NoteData,NoteAdapter.Holder>(MyDiffUtil) {
    private lateinit var onLongItemClickListener:(NoteData)->Unit
    private lateinit var onItemClickListener:(NoteData)->Unit
    object MyDiffUtil : DiffUtil.ItemCallback<NoteData>(){
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem == newItem
        }

    }

    inner class Holder(private val binding:ItemNoteBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.txtContent.setOnLongClickListener {
                onLongItemClickListener.invoke(getItem(adapterPosition))
                true
            }
            binding.txtContent.setOnClickListener {
                onItemClickListener.invoke(getItem(adapterPosition))
            }
        }

        fun bind(position:Int){
            binding.apply {
                val data = getItem(position)
                data.apply {
                    txtContent.isFocusable = false
                    txtContent.text = content.parseAsHtml()
                    txtTitle.text = title
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val timeFormat = SimpleDateFormat("HH:mm:ss")
                    val dateString:String =dateFormat.format(date)
                    val timeString:String = timeFormat.format(date)
                    txtDate.text = "$dateString\n$timeString"
                    if (pinned == 1){
                        binding.imgPin.visibility = View.VISIBLE
                    }else {
                        binding.imgPin.visibility = View.GONE
                    }
                    txtContent.setBackgroundResource(bgColor)
                }
            }

        }
    }

    fun setOnLongItemClickListener(block:(NoteData)->Unit){
        onLongItemClickListener = block
    }

    fun setOnItemClickListener(block: (NoteData) -> Unit){
        onItemClickListener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

}