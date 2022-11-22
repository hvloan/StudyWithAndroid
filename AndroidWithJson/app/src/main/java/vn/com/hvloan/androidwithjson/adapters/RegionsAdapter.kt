package vn.com.hvloan.androidwithjson.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.com.hvloan.androidwithjson.R
import vn.com.hvloan.androidwithjson.models.MyCountry

class RegionsAdapter(private val context: Context, private val regionsList: List<MyCountry>): RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {

    var onItemClick: ((MyCountry) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameRegion: TextView = view.findViewById(R.id.itemNameRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_region, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameRegion.text = regionsList[position].region

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(regionsList[position])
        }
    }

    override fun getItemCount(): Int {
        return regionsList.size
    }
}