import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yudhistira.uaskelompok5.DBModel
import com.yudhistira.uaskelompok5.R

class DBAdapter(private val listData:ArrayList<DBModel>):RecyclerView.Adapter<DBAdapter.CardViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    interface OnItemClickCallback {
        fun onItemClicked(data: DBModel)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    inner class CardViewHolder(itemV:View):RecyclerView.ViewHolder(itemV) {
        var Nama: TextView = itemV.findViewById(R.id.tvNama)
        var Usia: TextView = itemV.findViewById(R.id.tvAge)
        var cvData: CardView = itemV.findViewById(R.id.cvData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBAdapter.CardViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DBAdapter.CardViewHolder, position: Int) {
        val data = listData[position]
        holder.Nama.text = data.Nama
        holder.Usia.text = data.Usia
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


}