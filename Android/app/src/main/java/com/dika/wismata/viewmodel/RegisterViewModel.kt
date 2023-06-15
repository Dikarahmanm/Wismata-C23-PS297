package com.dika.wismata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dika.wismata.network.database.Repository
import com.dika.wismata.network.model.Register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    fun doRegister(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        role: String
    ) {
        val client = repository.userRegister(email, firstName, lastName, password, role)
        client.enqueue(
            object : Callback<Register> {
                override fun onResponse(
                    call: Call<Register>, response: Response<Register>
                ) {
                    _message.value = response.body()?.message
                }

                override fun onFailure(call: Call<Register>, t: Throwable) {
                    _message.value = "Server Error"
                }
            }
        )
    }
}