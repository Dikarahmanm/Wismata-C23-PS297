package com.dika.wismata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dika.wismata.R
import com.dika.wismata.databinding.ItemWisataBinding
import com.dika.wismata.network.model.Wisata
import com.dika.wismata.network.model.WisataMain

class MainAdapter(
    private val context: Context
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val WisataMain = ArrayList<WisataMain>()

    fun setListWisata(wisataMain: List<WisataMain>) {
        this.WisataMain.clear()
        this.WisataMain.addAll(wisataMain)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWisataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(WisataMain[position])
    }

    override fun getItemCount(): Int {
        return WisataMain.size
    }

    inner class ViewHolder(private val binding: ItemWisataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(wisataMain: WisataMain) {
            with(binding) {
                tvWisata.text = wisataMain.data?.namaWisata
                tvDestinationRating.text = wisataMain.data?.rating
                Glide.with(itemView.context)
                    .load(wisataMain.data?.fotoWisata)
                    .into(binding.ivWisata)
            }
        }
    }
}
