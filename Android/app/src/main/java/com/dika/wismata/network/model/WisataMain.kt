import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WisataMain(
    @SerializedName("namaWisata")
    val namaWisata: String? = null,

    @SerializedName("rating")
    val rating: String? = null,

    @SerializedName("fotoWisata")
    val fotoWisata: String? = null
) : Parcelable
