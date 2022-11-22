package vn.com.hvloan.androidwithjson.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import vn.com.hvloan.androidwithjson.R
import vn.com.hvloan.androidwithjson.models.MyCountry
import java.util.*
import kotlin.collections.ArrayList

class CountrySearchAdapter(context: Context, resource: Int, objects: ArrayList<MyCountry>) : ArrayAdapter<MyCountry>(context, resource, objects) {
    private val listSearchCountry: List<MyCountry>

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertview = convertView
        if (convertview == null) {
            convertview = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        }
        val itemImageSearch = convertview!!.findViewById<ImageView>(R.id.itemImgSearch)
        val itemNameSearch = convertview.findViewById<TextView>(R.id.itemSearchName)
        val country = getItem(position)
        val urlFlagCountry = "https://www.worldatlas.com/r/w960-q80/img/flag/${country?.iso2?.lowercase()}-flag.jpg"
        Glide.with(context).load(urlFlagCountry).into(itemImageSearch)
        itemNameSearch?.text = country?.name
        return convertview
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val listSuggest: ArrayList<MyCountry?> = ArrayList()
                if (constraint == null || constraint.isEmpty()) {
                    listSuggest.addAll(listSearchCountry)
                } else {
                    val filter = constraint.toString().lowercase(Locale.ROOT).trim()
                    for (p in listSearchCountry) {
                        if (p.name.lowercase(Locale.ROOT).contains(filter)) {
                            listSuggest.add(p)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = listSuggest
                filterResults.count = listSuggest.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                addAll(results?.values as ArrayList<MyCountry>)
                notifyDataSetInvalidated()
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as MyCountry).name
            }
        }
    }

    init {
        listSearchCountry = ArrayList(objects)
    }
}