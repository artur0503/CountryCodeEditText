package com.pulse.countrycodeedittext

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

internal class CountryCodeTextWatcher(
    private val editText: CountryCodeEditText,
    private var countryList: List<CountryModel>
) : TextWatcher, View.OnKeyListener {

    private var selectedFlag: String = EMPTY_FLAG
    private val phoneUtil = PhoneNumberUtil.getInstance()

    init {
        editText.addTextChangedListener(this)
        editText.setOnKeyListener(this)
    }

    companion object {
        private const val PLUS = "+"
        private const val EMPTY_FLAG = "\uD83C\uDFF3"
        private val DEFAULT_COUNTRY = CountryModel("Unknown country", "UNKNOWN", "-1", EMPTY_FLAG)
    }

    override fun afterTextChanged(s: Editable?) = editText.setSelection(editText.text.toString().length)

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        editText.removeTextChangedListener(this)
        preparePlus(editText.text.toString())
        if (editText.isEnableFlag)
            prepareFlag(editText.text.toString())
        editText.addTextChangedListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?) = when (keyCode) {
        KeyEvent.KEYCODE_DEL -> false
        else -> editText.text.toString().replace(selectedFlag, "").replace(
            PLUS,
            ""
        ).length >= editText.numberLength
    }

    private fun setFlag(country: CountryModel?, phone: String, isCorrect: Boolean) {
        country?.let {
            editText.setText(
                when {
                    phone.contains(selectedFlag) -> phone.replace(selectedFlag, it.emoji)
                    else -> phone.appendStart(it.emoji)
                }
            )
            editText.isCorrectNumber = isCorrect
            editText.selectedCountry = it
            selectedFlag = it.emoji
            val string = phone.getOnlyPhone()
            if (editText.numberChangeListener.isNotNull())
                editText.numberChangeListener?.onCountryChange(string, it)
        }
    }

    private fun prepareFlag(phone: String) {
        if (phone.isNotEmpty() && phone.contains(PLUS)) {
            val country: CountryModel?
            try {
                var list =
                    countryList.filter { it.dialCode.contains(phone.replace(selectedFlag, "")) }
                list = list.sortedBy { it.dialCode.length }
                country = if (list.isNotEmpty()) {
                    if (list.isNotEmpty()) list.first() else null
                } else {
                    val phoneNumber = phoneUtil.parse(phone.replace(selectedFlag, ""), "")
                    countryList.find { it.code == phoneUtil.getRegionCodeForNumber(phoneNumber) }
                }
                if (country.isNotNull())
                    setFlag(country, phone, true)
            } catch (e: NumberParseException) {
                setFlag(DEFAULT_COUNTRY, phone, false)
            }
        }
    }

    private fun preparePlus(string: String) {
        if (string.replace(selectedFlag, "").length == 1) {
            when (!string.contains(PLUS)) {
                true -> editText.setText(string.appendStart(PLUS))
                else -> editText.setText("")
            }
        }
    }
}