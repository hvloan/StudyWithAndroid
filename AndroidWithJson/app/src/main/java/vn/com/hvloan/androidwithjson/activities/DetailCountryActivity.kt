package vn.com.hvloan.androidwithjson.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import vn.com.hvloan.androidwithjson.R
import vn.com.hvloan.androidwithjson.models.MyCountry

class DetailCountryActivity : AppCompatActivity() {

    lateinit var toolbarDetailCountry: Toolbar
    lateinit var flagDetailCountry: ImageView
    private lateinit var nameDetailCountry: TextView
    private lateinit var isoDetailCountry: TextView
    private lateinit var phoneCodeDetailCountry: TextView
    private lateinit var btnVisibleCurrency: RoundedImageView
    lateinit var currencyDetailCountry: TextView
    lateinit var currencyNameDetailCountry: TextView
    lateinit var currencySymbolDetailCountry: TextView
    lateinit var regionDetailCountry: TextView
    lateinit var timezoneDetailCountry: TextView
    lateinit var capitalDetailCountry: TextView
    lateinit var statesDetailCountry: TextView
    lateinit var listViewSates: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_country)

        initComponent()
        setupToolbar()
        getDataTransfer()
        actionComponent()
    }

    private fun actionComponent() {
        btnVisibleCurrency.setOnClickListener {
            checkVisibleComponent(currencyDetailCountry)
            checkVisibleComponent(currencyNameDetailCountry)
            checkVisibleComponent(currencySymbolDetailCountry)
        }
    }

    private fun checkVisibleComponent(textView: TextView) {
        if (textView.visibility == View.GONE) {
            textView.visibility = View.VISIBLE
        } else if (textView.visibility == View.VISIBLE) {
            textView.visibility = View.GONE
        }
    }

    private fun getDataTransfer() {
        var countryModel: MyCountry? = null
        val obj = intent.getSerializableExtra("DATA")
        if (obj is MyCountry) {
            countryModel = obj
        }
        if (countryModel != null) {
            setDataComponent(countryModel)
            setupListViewCountry(countryModel)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataComponent(countryModel: MyCountry) {
        val urlFlagCountry = "https://www.worldatlas.com/r/w960-q80/img/flag/${countryModel.iso2.lowercase()}-flag.jpg"
        Glide
            .with(applicationContext)
            .load(urlFlagCountry)
            .into(flagDetailCountry)

        nameDetailCountry.text = countryModel.name
        isoDetailCountry.text = countryModel.iso2
        phoneCodeDetailCountry.text = countryModel.phone_code
        currencyDetailCountry.text = countryModel.currency
        currencyNameDetailCountry.text = countryModel.currency_name
        currencySymbolDetailCountry.text = countryModel.currency_symbol
        regionDetailCountry.text = "${countryModel.region} - ${countryModel.subregion}"
        timezoneDetailCountry.text = "${countryModel.timezones[0].zoneName} - ${countryModel.timezones[0].gmtOffsetName}"
        capitalDetailCountry.text = countryModel.capital
        statesDetailCountry.text = countryModel.states.size.toString()
    }

    private fun setupListViewCountry(countryModel: MyCountry) {
        val arrayAdapter: ArrayAdapter<*>
        val listStatesCountry = ArrayList<String>()
        countryModel.states.forEach {
            listStatesCountry.add(it.name)
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listStatesCountry)
        listViewSates.adapter = arrayAdapter

    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbarDetailCountry))

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true);
        actionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initComponent() {
        flagDetailCountry = findViewById(R.id.flagDetailCountry)
        nameDetailCountry = findViewById(R.id.nameDetailCountry)
        isoDetailCountry = findViewById(R.id.isoDetailCountry)
        phoneCodeDetailCountry = findViewById(R.id.phoneCodeDetailCountry)
        btnVisibleCurrency = findViewById(R.id.btnVisibleCurrency)
        currencyDetailCountry = findViewById(R.id.currencyDetailCountry)
        currencyNameDetailCountry = findViewById(R.id.currencyNameDetailCountry)
        currencySymbolDetailCountry = findViewById(R.id.currencySymbolDetailCountry)
        regionDetailCountry = findViewById(R.id.regionDetailCountry)
        timezoneDetailCountry = findViewById(R.id.timezoneDetailCountry)
        capitalDetailCountry = findViewById(R.id.capitalDetailCountry)
        statesDetailCountry = findViewById(R.id.statesDetailCountry)
        listViewSates = findViewById(R.id.listViewSates)

    }
}