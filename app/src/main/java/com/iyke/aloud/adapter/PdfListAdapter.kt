package com.iyke.aloud.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iyke.aloud.databinding.PdfItemviewBinding
import com.iyke.aloud.model.PdfFile


class PdfListAdapter(private val list: ArrayList<PdfFile>) : RecyclerView.Adapter<PdfListAdapter.PdfViewHolder>() {

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

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}