package com.dika.wismata.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dika.wismata.R
import com.dika.wismata.adapter.MainAdapter
import com.dika.wismata.databinding.ActivityMainBinding
import com.dika.wismata.network.model.WisataMain
import com.dika.wismata.viewmodel.MainViewModel
import com.dika.wismata.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
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

        wisataAdapter = MainAdapter(this)

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
        doLoading(true)
        viewModel.message.observe(this) { message ->
            Log.e("MainActivity", message.toString())
        }
    }

    private fun doLoading(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}