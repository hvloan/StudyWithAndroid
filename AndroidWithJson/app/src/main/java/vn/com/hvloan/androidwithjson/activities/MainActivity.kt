package vn.com.hvloan.androidwithjson.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.com.hvloan.androidwithjson.R
import vn.com.hvloan.androidwithjson.adapters.CountriesAdapter
import vn.com.hvloan.androidwithjson.adapters.CountrySearchAdapter
import vn.com.hvloan.androidwithjson.models.MyCountry
import vn.com.hvloan.androidwithjson.services.CountryService
import vn.com.hvloan.androidwithjson.services.ServiceBuilder


class MainActivity : AppCompatActivity() {

    lateinit var rcvCountries: RecyclerView
    lateinit var toolbarHome: Toolbar
    lateinit var countriesAdapter: CountriesAdapter
    private lateinit var searchView: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        setupToolbar()
        loadCountries()
    }

    private fun initComponent() {
        rcvCountries = findViewById(R.id.rcvCounties)
        toolbarHome = findViewById(R.id.toolbarHome)
    }

    private fun setupCountrySearchAdapter(listCountries: MutableList<MyCountry>) {
        val countrySearchAdapter = CountrySearchAdapter(
            this,
            R.layout.item_search,
            listCountries as ArrayList<MyCountry>
        )
        searchView.setAdapter(countrySearchAdapter)
        searchView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("data", listProduct[position])
//            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarHome)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        menuInflater.inflate(R.menu.item_menu_toolbar_home, menu)

        val itemSearch = menu!!.findItem(R.id.action_search)
        searchView = itemSearch.actionView as AutoCompleteTextView
        searchView.hint = "What are you finding?"
        searchView.width = width
        return true
    }

    private fun loadCountries() {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(CountryService::class.java)
        val requestCall = destinationService.getAllCountryList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<MyCountry>> {
            override fun onResponse(call: Call<List<MyCountry>>, response: Response<List<MyCountry>>) {
                if (response.isSuccessful){
                    val countryList  = response.body()!!
                    countriesAdapter = CountriesAdapter(this@MainActivity, countryList)
                    rcvCountries.layoutManager = GridLayoutManager(this@MainActivity,2)
                    rcvCountries.adapter = countriesAdapter
                    //set data for search adapter
                    setupCountrySearchAdapter(countryList as MutableList<MyCountry>)
                }else{
                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<MyCountry>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}