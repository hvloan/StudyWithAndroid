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


class CountriesAdapter(private val context: Context, private val countriesList: List<MyCountry>): RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    var onItemClick: ((MyCountry) -> Unit)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nameCountry: TextView = view.findViewById(R.id.nameCountry)
        val imgFlagCountry: ImageView = view.findViewById(R.id.flagCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_country,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameCountry.text = countriesList[position].name
        val urlFlagCountry = "https://www.worldatlas.com/r/w960-q80/img/flag/${countriesList[position].iso2.lowercase()}-flag.jpg"
        Glide
            .with(context)
            .load(urlFlagCountry)
            .into(holder.imgFlagCountry)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(countriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }
}