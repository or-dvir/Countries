package or_dvir.hotmail.com.countries.other

import or_dvir.hotmail.com.countries.model.Country
import retrofit2.Call
import retrofit2.http.*

interface ICountriesClient
{
    @GET("all")
    fun getAllCountries(): Call<List<Country>>
}
