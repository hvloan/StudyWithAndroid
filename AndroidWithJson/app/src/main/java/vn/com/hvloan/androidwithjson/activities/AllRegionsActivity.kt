package vn.com.hvloan.androidwithjson.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import vn.com.hvloan.androidwithjson.models.MyCountry
import vn.com.hvloan.androidwithjson.services.CountryService
import vn.com.hvloan.androidwithjson.services.ServiceBuilder
import kotlin.math.log

class AllRegionsActivity : AppCompatActivity() {

    lateinit var spinnerRegion: Spinner
    lateinit var spinnerSubRegion: Spinner
    lateinit var toolbarAllRegions: Toolbar
    lateinit var rcvFilterCountry: RecyclerView

    lateinit var countriesList: List<MyCountry>
    lateinit var regionsList: List<MyCountry>
    lateinit var subRegionList: List<MyCountry>

    lateinit var arrayRegionAdapter: ArrayAdapter<String>
    lateinit var arraySubRegionAdapter: ArrayAdapter<String>
    lateinit var filterCountriesAdapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_regions)

        initComponent()
        setupToolbar()
        loadData()
    }

    private fun setupRegionSpinner(regionsList: List<MyCountry>) {
        val nameRegionList = ArrayList<String>()
        regionsList.forEach {
            nameRegionList.add(it.region)
        }

        arrayRegionAdapter = ArrayAdapter(applicationContext, R.layout.item_spin_region, nameRegionList)
        spinnerRegion.adapter = arrayRegionAdapter

        spinnerRegion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                val nameRegionSelected = nameRegionList[position]
                setupSubRegionSpinner(nameRegionSelected)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun setupSubRegionSpinner(nameRegionSelected: String) {
        val nameSubRegionList = ArrayList<String>()
        subRegionList.forEach {
            if (it.region == nameRegionSelected) {
                nameSubRegionList.add(it.subregion)
            }
        }
        arraySubRegionAdapter = ArrayAdapter(applicationContext, R.layout.item_spin_region, nameSubRegionList)
        spinnerSubRegion.adapter = arraySubRegionAdapter

        spinnerSubRegion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                val nameSubRegionSelected = nameSubRegionList[position]
                Log.d("DTB", "$nameRegionSelected - $nameSubRegionSelected")
                setupRcvFilterCountry(nameSubRegionSelected)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun setupRcvFilterCountry( nameSubRegionSelected: String) {
        val filterCountriesList = ArrayList<MyCountry>()
        countriesList.forEach {
            if (it.subregion == nameSubRegionSelected) {
                filterCountriesList.add(it)
            }
        }
        filterCountriesAdapter = CountriesAdapter(this@AllRegionsActivity, filterCountriesList)
        rcvFilterCountry.visibility = View.VISIBLE
        rcvFilterCountry.layoutManager = GridLayoutManager(this@AllRegionsActivity,2)
        rcvFilterCountry.adapter = filterCountriesAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initComponent() {
        spinnerRegion = findViewById(R.id.spinRegion)
        spinnerSubRegion = findViewById(R.id.spinSubRegion)
        toolbarAllRegions = findViewById(R.id.toolbarAllRegions)
        rcvFilterCountry = findViewById(R.id.rcvFilterCountry)
        countriesList = ArrayList()
        regionsList = ArrayList()
        subRegionList = ArrayList()
    }

    fun getDataRegion(list: List<MyCountry>): List<MyCountry> {
        return list.distinctBy { it.region }.toList()
    }

    fun getDataSubRegion(list: List<MyCountry>): List<MyCountry> {
        return list.distinctBy { it.subregion }.toList()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbarAllRegions))

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true);
        actionBar?.setDisplayShowHomeEnabled(true);
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
                    //set data for regions
                    regionsList = getDataRegion(countriesList)
                    setupRegionSpinner(regionsList)
                    //set data for sub regions
                    subRegionList = getDataSubRegion(countriesList)
                    subRegionList.forEach {
                        Log.d("TAG DATA: ", it.subregion)
                    }
                }else{
                    Toast.makeText(applicationContext, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<MyCountry>>, t: Throwable) {
                Toast.makeText(applicationContext, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}