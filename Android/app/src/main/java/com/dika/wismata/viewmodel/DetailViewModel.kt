package com.dika.wismata.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dika.wismata.network.database.Repository
import com.dika.wismata.network.model.DetailWisataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    val detailWisata = MutableLiveData<DetailWisataModel>()

    lateinit var listUmkm: LiveData<List<DetailWisataModel>>

    fun getDetailWisata(): LiveData<DetailWisataModel> {
        return detailWisata
    }

    fun detailWisata(id: String) {
        val client = repository.wisataById(id)
        client.enqueue(object : Callback<DetailWisataModel> {
            override fun onResponse(
                call: Call<DetailWisataModel>,
                response: Response<DetailWisataModel>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    detailWisata.postValue(responseBody!!)
                    _message.value = "Success"
                }
            }

            override fun onFailure(call: Call<DetailWisataModel>, t: Throwable) {
                _message.value = "Server Error"
            }
        })
    }
}