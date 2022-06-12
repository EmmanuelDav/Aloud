package com.iyke.aloud.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iyke.aloud.databinding.PdfItemviewBinding
import com.iyke.aloud.model.PdfFile


class PdfListAdapter( val onClickListener: OnClickListener) : ListAdapter<PdfFile,PdfListAdapter.PdfViewHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<PdfFile>() {
        override fun areItemsTheSame(oldItem: PdfFile, newItem: PdfFile): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PdfFile, newItem: PdfFile): Boolean {
            return oldItem.files == newItem.files
        }
    }

    class PdfViewHolder(val view: PdfItemviewBinding) :RecyclerView.ViewHolder(view.root) {
        fun bind(item: PdfFile) {
            view.file = item
            view.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PdfViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PdfItemviewBinding.inflate(inflater)
        return PdfViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val meme = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(meme)
        }
        holder.bind(meme)
    }

    class OnClickListener(val clickListener: (meme: PdfFile) -> Unit) {
        fun onClick(meme: PdfFile) = clickListener(meme)
    }
}