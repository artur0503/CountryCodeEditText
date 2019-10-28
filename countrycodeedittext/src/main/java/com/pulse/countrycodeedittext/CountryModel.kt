package com.pulse.countrycodeedittext

open class CountryModel(
    val name: String,
    val code: String,
    val dialCode: String,
    val emoji: String
)

internal class CountryCodeModel(
    val name: String,
    val dial_code: String,
    val code: String
)

internal class CountryEmojiModel(
    val code: String,
    val emoji: String
)