package com.dika.wismata.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dika.wismata.databinding.ItemWisataBinding
import com.dika.wismata.network.model.DataItem
import com.dika.wismata.utils.OnItemClickAdapter

class MainAdapter(
    private val context: Context,
    private val clickListener: OnItemClickAdapter
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val DataItem = ArrayList<DataItem>()

    fun setListWisata(dataItem: List<DataItem>) {
        DataItem.clear()
        DataItem.addAll(dataItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWisataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(DataItem[position])
    }

    override fun getItemCount(): Int {
        return minOf(DataItem.size, 50)
    }

    inner class ViewHolder(private val binding: ItemWisataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dataItem: DataItem) {
            with(binding) {
                tvWisata.text = dataItem.namaWisata
                tvDestinationRating.text = dataItem.rating.toString()
                Glide.with(itemView.context)
                    .load(dataItem.fotoWisata)
                    .into(binding.ivWisata)
                root.setOnClickListener {
                    clickListener.onItemClick(
                        dataItem,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            context as Activity,
                            binding.btnArrow,
                            "image"
                        )
                    )
                }
            }
        }
    }
}
