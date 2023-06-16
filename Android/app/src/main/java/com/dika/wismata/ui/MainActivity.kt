package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }

        getWisata()
    }

    private fun getWisata() {
        viewModel.setWisata()
        viewModel.getListWisata().observe(this) { wisataMain ->
            if (wisataMain != null) {
                wisataAdapter.setListWisata(wisataMain.data as List<DataItem>)
            }
        }
    }

    override fun onItemClick(dataItem: DataItem, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, dataItem.idWisata)
        startActivity(intent, optionsCompat.toBundle())
    }
}
