package vn.com.hvloan.androidwithjson.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.com.hvloan.androidwithjson.R
import vn.com.hvloan.androidwithjson.adapters.CountriesAdapter
import vn.com.hvloan.androidwithjson.adapters.CountrySearchAdapter
import vn.com.hvloan.androidwithjson.adapters.RegionsAdapter
import vn.com.hvloan.androidwithjson.models.MyCountry
import vn.com.hvloan.androidwithjson.services.CountryService
import vn.com.hvloan.androidwithjson.services.ServiceBuilder


class MainActivity : AppCompatActivity() {

    lateinit var rcvCountries: RecyclerView
    lateinit var rcvRegions: RecyclerView
    lateinit var toolbarHome: Toolbar
    lateinit var countriesAdapter: CountriesAdapter
    lateinit var regionsAdapter: RegionsAdapter
    private lateinit var searchView: AutoCompleteTextView
    lateinit var countriesList: List<MyCountry>
    lateinit var regionsList: List<MyCountry>
    private var widthScreen = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent(applicationContext)
        setupToolbar()
        loadData()
    }

    private fun initComponent(context: Context) {
        rcvCountries = findViewById(R.id.rcvCountries)
        rcvRegions = findViewById(R.id.rcvRegion)
        toolbarHome = findViewById(R.id.toolbarHome)
        countriesList = ArrayList()
        regionsList = ArrayList()
        widthScreen = context.resources.displayMetrics.widthPixels
    }

    private fun setupCountrySearchAdapter(listCountries: List<MyCountry>) {
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
        menuInflater.inflate(R.menu.item_menu_toolbar_home, menu)

        val itemSearch = menu!!.findItem(R.id.action_search)
        searchView = itemSearch.actionView as AutoCompleteTextView
        searchView.hint = "What are you finding?"
        searchView.width = widthScreen
        return true
    }

    fun getDataRegion(list: List<MyCountry>): List<MyCountry> {
        return list.distinctBy { it.region }.toList()
    }

    private fun loadData() {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(CountryService::class.java)
        val requestCall = destinationService.getAllCountryList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<MyCountry>> {
            override fun onResponse(call: Call<List<MyCountry>>, response: Response<List<MyCountry>>) {
                if (response.isSuccessful){
                    countriesList  = response.body()!!
                    //set data for countries adapter
                    countriesAdapter = CountriesAdapter(this@MainActivity, countriesList)
                    rcvCountries.layoutManager = GridLayoutManager(this@MainActivity,2)
                    rcvCountries.adapter = countriesAdapter
                    //set data for regions adapter
                    regionsList = getDataRegion(countriesList)
                    regionsAdapter = RegionsAdapter(this@MainActivity, regionsList)
                    rcvRegions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                    rcvRegions.adapter = regionsAdapter
                    //set data for search adapter
                    setupCountrySearchAdapter(countriesList)
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