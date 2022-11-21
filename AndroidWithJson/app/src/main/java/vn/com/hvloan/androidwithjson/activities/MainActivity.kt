package vn.com.hvloan.androidwithjson.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

class MainActivity : AppCompatActivity() {

    lateinit var rcvCountries: RecyclerView
    lateinit var countriesAdapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        loadCountries()

    }

    private fun initComponent() {
        rcvCountries = findViewById(R.id.rcvCounties)
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