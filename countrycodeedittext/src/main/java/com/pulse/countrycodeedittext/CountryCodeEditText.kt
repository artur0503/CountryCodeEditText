package com.pulse.countrycodeedittext

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.widget.EditText


open class CountryCodeEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : EditText(context, attrs, defStyleAttr) {

    interface TextChangeListener {
        fun afterTextChanged(s: Editable?)
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
        fun onCountryChange(country : CountryModel)
    }

    var textChangeListener : TextChangeListener? = null
    private var countryCodeTextWatcher: CountryCodeTextWatcher? = null

    /**return data by editText**/
    var isCorrectNumber = false
    var selectedCountry : CountryModel? = null

    /**set data by editText**/
    var numberLength : Int = DEFAULT_COUNT
    var isEnableFlag = true

    companion object {
        private const val DEFAULT_COUNT = 15
        private const val DEFAULT_ENABLE_FLAGS = true
        private const val NUMBERS = "0123456789"
    }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CountryCodeEditText, 0, 0)
            isEnableFlag = typedArray.getBoolean(R.styleable.CountryCodeEditText_isFlagEnable, DEFAULT_ENABLE_FLAGS)
            numberLength = typedArray.getInteger(R.styleable.CountryCodeEditText_numberLength, DEFAULT_COUNT)
            typedArray.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        countryCodeTextWatcher = CountryCodeTextWatcher(this, CountryListJsonParser(context).getMergedCountriesList())
        inputType = InputType.TYPE_CLASS_PHONE + InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        keyListener = DigitsKeyListener.getInstance(NUMBERS)
    }






}