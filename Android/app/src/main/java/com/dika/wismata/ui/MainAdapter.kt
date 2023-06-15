import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dika.wismata.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val listWisata = mutableListOf<WisataMain>()

    fun setListWisata(listWisata: List<WisataMain>) {
        this.listWisata.clear()
        this.listWisata.addAll(listWisata)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(wisataMain: WisataMain) {
            with(itemView) {
                tvWisata.text = wisataMain.namaWisata
                tvDestinationRating.text = wisataMain.rating
                Glide.with(itemView.context)
                    .load(wisataMain.fotoWisata)
                    .into(iv_wisata)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wisata, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wisataMain = listWisata[position]
        holder.bindItem(wisataMain)
    }

    override fun getItemCount(): Int {
        return listWisata.size
    }
}
