import android.util.Log
import androidx.lifecycle.ViewModel
import com.dika.wismata.network.api.ApiService
import com.dika.wismata.network.api.ApiConfig
import com.dika.wismata.network.model.WisataMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listWisata = mutableListOf<WisataMain>()

    fun setWisata() {
        val apiService: ApiService = ApiConfig.getApiService()
        apiService.getAllWisata().enqueue(object : Callback<List<WisataMain>> {
            override fun onResponse(
                call: Call<List<WisataMain>>,
                response: Response<List<WisataMain>>
            ) {
                if (response.isSuccessful) {
                    val wisataList = response.body()
                    wisataList?.let {
                        listWisata.addAll(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<WisataMain>>, t: Throwable) {
                Log.d("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getWisata(): List<WisataMain> {
        return listWisata
    }
}
