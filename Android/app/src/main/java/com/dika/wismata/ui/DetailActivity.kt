package com.dika.wismata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dika.wismata.R
import com.dika.wismata.adapter.UmkmAdapter
import com.dika.wismata.databinding.ActivityDetailBinding
import com.dika.wismata.network.model.UmkmItem
import com.dika.wismata.viewmodel.DetailViewModel
import com.dika.wismata.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var umkmAdapter: UmkmAdapter
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val recyclerView: RecyclerView
        get() = binding.rvUmkm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        umkmAdapter = UmkmAdapter(this)

        supportActionBar?.title = getString(R.string.detail_destination)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            rvUmkm.layoutManager = LinearLayoutManager(this@DetailActivity)
            rvUmkm.adapter = umkmAdapter
        }

        getDetail()
        getUmkm()
        ratingStar()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun doLoading(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getDetail() {
        val id = intent.getStringExtra(EXTRA_ID)
        viewModel.detailWisata(id.toString())
        doLoading(true)

        viewModel.message.observe(this) { message ->
            if (message == "Success") {
                viewModel.getDetailWisata().observe(this) { wisata ->
                    doLoading(false)
                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(wisata.wisata?.fotoWisata)
                            .into(imageViewDetail)
                    }
                    binding.tvDestinationName.text = wisata.wisata?.namaWisata
                    binding.tvDestinationAddress.text = wisata.wisata?.kota
                    binding.tvDestinationDescription.text = wisata.wisata?.deskripsiWisata
                    binding.tvDestinationRating.text = "Rating: ${wisata.wisata?.rating}"
                    binding.tvDestinationPrice.text = "Rp. ${wisata.wisata?.harga}"
                    binding.tvDestinationCategory.text = wisata.wisata?.kategoriWisata
                }
            } else {
                doLoading(false)
                Toast.makeText(this, getString(R.string.failed_to_get_data), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun getUmkm() {
        binding.rvUmkm.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = umkmAdapter
            setHasFixedSize(true)
        }
        viewModel.apply {
            doLoading(true)
            getDetailWisata().observe(this@DetailActivity) { umkm ->
                if (umkm != null) {
                    umkmAdapter.setUmkmItem(umkm.umkm as List<UmkmItem>)
                    doLoading(false)
                } else {
                    Toast.makeText(
                        this@DetailActivity,
                        getString(R.string.failed_to_get_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun ratingStar() {
        binding.ivStar1.setOnClickListener {
            binding.ivStar1.setImageResource(R.drawable.ic_star)
            binding.ivStar2.setImageResource(R.drawable.ic_star_disable)
            binding.ivStar3.setImageResource(R.drawable.ic_star_disable)
            binding.ivStar4.setImageResource(R.drawable.ic_star_disable)
            binding.ivStar5.setImageResource(R.drawable.ic_star_disable)
        }

        binding.ivStar2.setOnClickListener {
            binding.ivStar1.setImageResource(R.drawable.ic_star)
            binding.ivStar2.setImageResource(R.drawable.ic_star)
            binding.ivStar3.setImageResource(R.drawable.ic_star_disable)
            binding.ivStar4.setImageResource(R.drawable.ic_star_disable)
            binding.ivStar5.setImageResource(R.drawable.ic_star_disable)
        }

        binding.ivStar3.setOnClickListener {
            binding.ivStar1.setImageResource(R.drawable.ic_star)
            binding.ivStar2.setImageResource(R.drawable.ic_star)
            binding.ivStar3.setImageResource(R.drawable.ic_star)
            binding.ivStar4.setImageResource(R.drawable.ic_star_disable)
            binding.ivStar5.setImageResource(R.drawable.ic_star_disable)
        }

        binding.ivStar4.setOnClickListener {
            binding.ivStar1.setImageResource(R.drawable.ic_star)
            binding.ivStar2.setImageResource(R.drawable.ic_star)
            binding.ivStar3.setImageResource(R.drawable.ic_star)
            binding.ivStar4.setImageResource(R.drawable.ic_star)
            binding.ivStar5.setImageResource(R.drawable.ic_star_disable)
        }

        binding.ivStar5.setOnClickListener {
            binding.ivStar1.setImageResource(R.drawable.ic_star)
            binding.ivStar2.setImageResource(R.drawable.ic_star)
            binding.ivStar3.setImageResource(R.drawable.ic_star)
            binding.ivStar4.setImageResource(R.drawable.ic_star)
            binding.ivStar5.setImageResource(R.drawable.ic_star)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}