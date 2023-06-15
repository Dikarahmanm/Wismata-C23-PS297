package com.dika.wismata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dika.wismata.databinding.ItemUmkmBinding
import com.dika.wismata.network.model.UmkmItem

class UmkmAdapter(
    private val context: Context,
) : RecyclerView.Adapter<UmkmAdapter.ViewHolder>() {

    private val UmkmItem = ArrayList<UmkmItem>()

    fun setUmkmItem(umkmItem: List<UmkmItem>) {
        UmkmItem.clear()
        UmkmItem.addAll(umkmItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUmkmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(UmkmItem[position])
    }

    override fun getItemCount(): Int = UmkmItem.size

    inner class ViewHolder(private val binding: ItemUmkmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(umkmItem: UmkmItem) {
            with(binding) {
                tvUmkmName.text = umkmItem.namaUmkm
                tvUmkmDetails.text = umkmItem.deskripsiUmkm
                Glide.with(context)
                    .load(umkmItem.fotoUmkm)
                    .into(ivUmkm)
            }
        }
    }

}