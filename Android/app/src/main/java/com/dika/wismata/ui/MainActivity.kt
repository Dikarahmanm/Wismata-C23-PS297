package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dika.wismata.R
import com.dika.wismata.adapter.MainAdapter
import com.dika.wismata.databinding.ActivityMainBinding
import com.dika.wismata.network.model.DataItem
import com.dika.wismata.utils.OnItemClickAdapter
import com.dika.wismata.viewmodel.MainViewModel
import com.dika.wismata.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), OnItemClickAdapter {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wisataAdapter: MainAdapter
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wisataAdapter = MainAdapter(this, this)

        binding.rvRecomenWisata.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = wisataAdapter
            setHasFixedSize(true)
        }

        getWisata()
    }

    private fun getWisata() {
        doLoading(true)
        viewModel.setWisata()
        viewModel.getListWisata().observe(this) { wisataMain ->
            if (wisataMain != null) {
                doLoading(false)
                wisataAdapter.setListWisata(wisataMain.data as List<DataItem>)
            }else{
                doLoading(false)
                Toast.makeText(this, getString(R.string.failed_to_get_data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(dataItem: DataItem, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, dataItem.idWisata)
        startActivity(intent, optionsCompat.toBundle())
    }

    private fun doLoading(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
