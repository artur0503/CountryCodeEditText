package com.pulse.countrycodeedittext

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import com.google.i18n.phonenumbers.Phonenumber

open class CountryCodeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    interface NumberChangeListener {
        fun onCountryChange(phoneNumber: String, country: CountryModel)
    }

    var numberChangeListener: NumberChangeListener? = null
    private var countryCodeTextWatcher: CountryCodeTextWatcher? = null

    /**return data by editText**/
    var isCorrectNumber = false
    var selectedCountry: CountryModel? = null

    /**set data by editText**/
    var numberLength: Int = DEFAULT_COUNT
    var isEnableFlag = true

    companion object {
        private const val DEFAULT_COUNT = 15
        private const val DEFAULT_ENABLE_FLAGS = true
        private const val NUMBERS = "0123456789"
    }

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.CountryCodeEditText, 0, 0)
            isEnableFlag = typedArray.getBoolean(
                R.styleable.CountryCodeEditText_isFlagEnable,
                DEFAULT_ENABLE_FLAGS
            )
            numberLength =
                typedArray.getInteger(R.styleable.CountryCodeEditText_numberLength, DEFAULT_COUNT)
            typedArray.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        countryCodeTextWatcher =
            CountryCodeTextWatcher(this, CountryListJsonParser(context).getMergedCountriesList())
        inputType = InputType.TYPE_CLASS_PHONE + InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        keyListener = DigitsKeyListener.getInstance(NUMBERS)
    }


}