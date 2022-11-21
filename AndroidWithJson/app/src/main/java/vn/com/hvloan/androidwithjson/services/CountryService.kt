package vn.com.hvloan.androidwithjson.services

import retrofit2.Call
import retrofit2.http.GET
import vn.com.hvloan.androidwithjson.models.MyCountry

interface CountryService {
    @GET("jsonCountries.json")
    fun getAllCountryList () : Call<List<MyCountry>>
}