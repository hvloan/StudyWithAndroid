package vn.com.hvloan.androidwithjson.models

class MyCountry(
        val capital: String,
        val currency: String,
        val currency_name: String,
        val currency_symbol: String,
        val emoji: String,
        val emojiU: String,
        val iso2: String,
        val iso3: String,
        val latitude: String,
        val longitude: String,
        val name: String,
        val native: String,
        val numeric_code: String,
        val phone_code: String,
        val region: String,
        val states: List<State>,
        val subregion: String,
        val timezones: List<Timezone>,
        val tld: String,
        val translations: Translations
    ) {

    data class Translations(
        val cn: String,
        val de: String,
        val es: String,
        val fa: String,
        val fr: String,
        val hr: String,
        val it: String,
        val ja: String,
        val kr: String,
        val nl: String,
        val pt: String,
        val tr: String
    )

    data class Timezone(
        val abbreviation: String,
        val gmtOffset: Int,
        val gmtOffsetName: String,
        val tzName: String,
        val zoneName: String
    )

    data class State(
        val id: Int,
        val latitude: String,
        val longitude: String,
        val name: String,
        val state_code: String,
        val type: String
    )
}