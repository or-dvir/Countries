package or_dvir.hotmail.com.countries.other

import com.squareup.moshi.Moshi
import or_dvir.hotmail.com.countries.model.Country

object MyMoshi
{
    private val instance: Moshi = Moshi.Builder().build()
    private val countryAdapter = instance.adapter(Country::class.java)

    fun toJson(country: Country): String = countryAdapter.toJson(country)
    fun fromJson(json: String): Country? = countryAdapter.fromJson(json)
}