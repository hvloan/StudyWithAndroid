package vn.com.hvloan.androidwithjson.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.com.hvloan.androidwithjson.R
import vn.com.hvloan.androidwithjson.adapters.CountriesAdapter
import vn.com.hvloan.androidwithjson.adapters.RegionsAdapter
import vn.com.hvloan.androidwithjson.models.MyCountry
import vn.com.hvloan.androidwithjson.services.CountryService
import vn.com.hvloan.androidwithjson.services.ServiceBuilder


class MainActivity : AppCompatActivity() {

    lateinit var rcvCountries: RecyclerView
    lateinit var rcvRegions: RecyclerView
    lateinit var toolbarHome: Toolbar
    lateinit var btnViewAllRegions: TextView
    lateinit var countriesAdapter: CountriesAdapter
    lateinit var regionsAdapter: RegionsAdapter
    private lateinit var searchView: SearchView
    lateinit var countriesList: List<MyCountry>
    lateinit var regionsList: List<MyCountry>
    private var widthScreen = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent(applicationContext)
        loadData()
        setupToolbar()
        actionComponent()
    }

    private fun actionComponent() {
        btnViewAllRegions.setOnClickListener {
            val intent = Intent(this, AllRegionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponent(context: Context) {
        rcvCountries = findViewById(R.id.rcvCountries)
        rcvRegions = findViewById(R.id.rcvRegion)
        toolbarHome = findViewById(R.id.toolbarHome)
        btnViewAllRegions = findViewById(R.id.btnSeeAllRegions)
        countriesList = ArrayList()
        regionsList = ArrayList()
        widthScreen = context.resources.displayMetrics.widthPixels
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarHome)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu_toolbar_home, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = widthScreen
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                countriesAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countriesAdapter.filter.filter(newText)
                return false
            }

        })
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