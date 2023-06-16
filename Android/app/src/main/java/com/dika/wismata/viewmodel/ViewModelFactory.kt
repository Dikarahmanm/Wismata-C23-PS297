package com.dika.wismata.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dika.wismata.network.database.Repository
import com.dika.wismata.utils.Injection

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                repository
            ) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repository) as T

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository) as T

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }

    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.providerRepo(context))
            }.also { instance = it }
    }

}