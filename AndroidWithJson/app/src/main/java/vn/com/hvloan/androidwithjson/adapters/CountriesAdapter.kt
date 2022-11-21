package vn.com.hvloan.androidwithjson.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        Log.d("FLAG: ", "https://raw.githubusercontent.com/hvloan/StudyWithAndroid/main/flags/${countriesList[position].iso2.lowercase()}.svg")
        holder.nameCountry.text = countriesList[position].name
        val urlFlagCountry = "https://raw.githubusercontent.com/hvloan/StudyWithAndroid/main/flags/${countriesList[position].iso2.lowercase()}.svg"
//        Glide
//            .with(context)
//            .load(urlFlagCountry)
//            .apply()
//            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(countriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }
}