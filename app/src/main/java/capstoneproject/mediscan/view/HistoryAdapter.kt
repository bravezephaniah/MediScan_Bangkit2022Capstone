package capstoneproject.mediscan.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import capstoneproject.mediscan.R
import capstoneproject.mediscan.data.network.GetHistoryResponseItem
import com.bumptech.glide.Glide

class HistoryAdapter(private val listHistory: List<GetHistoryResponseItem?>?): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgHistory: ImageView = itemView.findViewById(R.id.img_history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listHistory?.get(position)?.imgUrl)
            .into(holder.imgHistory)
    }

    override fun getItemCount(): Int {
        return listHistory?.size ?: 0
    }
}