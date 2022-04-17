package com.example.corotion.data.response

import com.example.corotion.data.model.Country
import com.google.gson.annotations.SerializedName

data class SummaryResponse(
    @SerializedName("Global")
    val globalItem: GlobalItem?,
    @SerializedName("Countries")
    val countryItems: List<CountryItem>?
) {

    data class GlobalItem(
        @SerializedName("NewConfirmed")
        val newConfirmed: Int?,
        @SerializedName("TotalConfirmed")
        val totalConfirmed: Int?,
        @SerializedName("NewDeaths")
        val newDeaths: Int?,
        @SerializedName("TotalDeaths")
        val totalDeaths: Int?,
        @SerializedName("NewRecovered")
        val newRecovered: Int?,
        @SerializedName("TotalRecovered")
        val totalRecovered: Int?
    )

    data class CountryItem(
        @SerializedName("Country")
        val country: String?,
        @SerializedName("CountryCode")
        val countryCode: String?,
        @SerializedName("Slug")
        val slug: String?,
        @SerializedName("NewConfirmed")
        val newConfirmed: Int?,
        @SerializedName("TotalConfirmed")
        val totalConfirmed: Int?,
        @SerializedName("NewDeaths")
        val newDeaths: Int?,
        @SerializedName("TotalDeaths")
        val totalDeaths: Int?,
        @SerializedName("NewRecovered")
        val newRecovered: Int?,
        @SerializedName("TotalRecovered")
        val totalRecovered: Int?
    ) {
        fun toCountry(): Country {
            return Country(
                country.orEmpty(),
                countryCode.orEmpty(),
                slug.orEmpty(),
                newConfirmed ?: 0,
                totalConfirmed ?: 0,
                newDeaths ?: 0,
                totalDeaths ?: 0,
                newRecovered ?: 0,
                totalRecovered ?: 0
            )
        }
    }
}