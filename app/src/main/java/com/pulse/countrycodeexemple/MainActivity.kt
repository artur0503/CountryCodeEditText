package com.pulse.countrycodeexemple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import com.pulse.countrycodeedittext.CountryCodeEditText
import com.pulse.countrycodeedittext.CountryModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etCode.textChangeListener = object : CountryCodeEditText.TextChangeListener{
            override fun onCountryChange(country: CountryModel) {
                Log.v("TAG_TEST", country.code)
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
    }
}
