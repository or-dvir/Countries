package or_dvir.hotmail.com.countries.other

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SMyRetrofit
{
    val countriesClient: ICountriesClient

    init
    {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        countriesClient = retrofit.create(ICountriesClient::class.java)
    }
}