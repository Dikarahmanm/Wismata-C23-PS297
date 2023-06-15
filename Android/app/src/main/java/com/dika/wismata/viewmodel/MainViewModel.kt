package com.dika.wismata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dika.wismata.network.database.Repository
import com.dika.wismata.network.model.WisataMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    val listWisata = MutableLiveData<WisataMain>()

    fun getListWisata(): LiveData<WisataMain> {
        return listWisata
    }

    fun setWisata() {
        val client = repository.getRecomWisata()
        client.enqueue(object : Callback<WisataMain> {
            override fun onResponse(
                call: Call<WisataMain>,
                response: Response<WisataMain>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    listWisata.postValue(responseBody!!)
                    _message.value = "Success"
                }
            }

            override fun onFailure(call: Call<WisataMain>, t: Throwable) {
                _message.value = t.message.toString()
            }
        })
    }
}
