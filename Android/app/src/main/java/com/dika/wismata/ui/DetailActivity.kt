package com.dika.wismata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dika.wismata.R
import com.dika.wismata.databinding.ActivityDetailBinding
import com.dika.wismata.viewmodel.DetailViewModel
import com.dika.wismata.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_destination)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getDetail()
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
//        val id = intent.getStringExtra(EXTRA_ID)
        val id = 1
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

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}