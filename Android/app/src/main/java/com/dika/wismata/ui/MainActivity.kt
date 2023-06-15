package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dika.wismata.R
import com.dika.wismata.adapter.MainAdapter
import com.dika.wismata.databinding.ActivityMainBinding
import com.dika.wismata.network.model.DataItem
import com.dika.wismata.network.model.Wisata
import com.dika.wismata.utils.OnItemClickAdapter
import com.dika.wismata.viewmodel.MainViewModel
import com.dika.wismata.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), OnItemClickAdapter {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wisataAdapter: MainAdapter
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val recyclerView: RecyclerView
        get() = binding.rvRecomenWisata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wisataAdapter = MainAdapter(this, this)

        binding.apply {
            rvRecomenWisata.setHasFixedSize(true)
            rvRecomenWisata.layoutManager =
                LinearLayoutManager(this@MainActivity)
            rvRecomenWisata.adapter = wisataAdapter
        }
        getWisata()
    }

    private fun getWisata() {
        binding.rvRecomenWisata.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = wisataAdapter
            setHasFixedSize(true)
        }
        viewModel.setWisata()
        viewModel.apply {
            doLoading(true)
            getListWisata().observe(this@MainActivity) {
                if (it != null) {
                    doLoading(false)
                    wisataAdapter.setListWisata(it.data as List<DataItem>)
                }
            }

        }
    }
    private fun doLoading(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onItemClick(dataItem: DataItem, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, dataItem.idWisata)
        startActivity(intent, optionsCompat.toBundle())
    }
}

