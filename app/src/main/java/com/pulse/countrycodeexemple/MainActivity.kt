package com.pulse.countrycodeexemple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.pulse.countrycodeedittext.CountryCodeEditText
import com.pulse.countrycodeedittext.CountryModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etCode.numberChangeListener = object : CountryCodeEditText.NumberChangeListener{
            override fun onCountryChange(phoneNumber: String, country: CountryModel) {
                Log.v("TAG_NUMBER", phoneNumber)
            }
        }
    }
}
