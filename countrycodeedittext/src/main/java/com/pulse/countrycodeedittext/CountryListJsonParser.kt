package com.pulse.countrycodeedittext

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.ref.WeakReference


internal class CountryListJsonParser(context: Context){

    private var weakReference : WeakReference<Context> = WeakReference(context)

    private fun getJsonAsString(resId: Int): String {
        val inputStream = weakReference.get()!!.resources.openRawResource(resId)
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun getCountryList(): List<CountryCodeModel> {
        val jsonStr = getJsonAsString(R.raw.countries_code)
        val json = Gson()
        val list: List<CountryCodeModel> = json.fromJson(
            jsonStr,
            object : TypeToken<List<CountryCodeModel>>() {}.type
        )
        return list.toList()
    }

    private fun getCountryEmojiList(): List<CountryEmojiModel> {
        val jsonStr = getJsonAsString(R.raw.countries_emojis)
        val json = Gson()
        val list: List<CountryEmojiModel> = json.fromJson(
            jsonStr,
            object : TypeToken<List<CountryEmojiModel>>() {}.type
        )
        return list.toList()
    }

    fun getMergedCountriesList(): List<CountryModel> {
        val data = mutableListOf<CountryModel>()
        val countryCodeDataList = getCountryList()
        val countryEmojiDataList = getCountryEmojiList()
        for (countryCodeModel in countryCodeDataList) {
            val emojiCountry: CountryEmojiModel? =
                countryEmojiDataList.firstOrNull { it.code == countryCodeModel.code }
            val adapterCountryItem =
                if (emojiCountry != null) {
                    CountryModel(
                        countryCodeModel.name,
                        countryCodeModel.code,
                        countryCodeModel.dial_code.removeWhitespaces(),
                        emojiCountry.emoji
                    )
                } else {
                    CountryModel(
                        countryCodeModel.name,
                        countryCodeModel.code,
                        countryCodeModel.dial_code,
                        ""
                    )
                }
            data += adapterCountryItem
        }
        return data.toList()
    }


}