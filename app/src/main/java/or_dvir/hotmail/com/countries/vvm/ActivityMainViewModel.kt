package or_dvir.hotmail.com.countries.vvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import or_dvir.hotmail.com.countries.model.Country
import or_dvir.hotmail.com.countries.other.MyMoshi
import or_dvir.hotmail.com.countries.other.SMyRetrofit
import or_dvir.hotmail.com.dxutils.RetroCallback
import or_dvir.hotmail.com.dxutils.retroRequestAsync
import kotlin.coroutines.coroutineContext

class ActivityMainViewModel(app: Application): AndroidViewModel(app)
{
    private val TAG = "ActivityMainViewModel"
    val mCountries = MutableLiveData<List<Country>>()

    init
    {
        getAllCountries()
    }

    suspend fun sortByName(ascending: Boolean = true)
    {
        val newList = mCountries.value?.toMutableList()
        mCountries.value = withContext(coroutineContext) {
            newList?.apply {
                if(ascending)
                    sortBy { it._EnglishName }
                else
                    sortByDescending { it._EnglishName}
            }
        }
    }

    fun getBordersAsJsonList(country: Country): List<String>
    {
        val list = mutableListOf<String>()
        country._Borders.forEach { b ->
            mCountries.value?.find { it._Alpha3Code == b }?.apply {
                list.add(MyMoshi.toJson(this))
            }
        }

        return list
    }

    suspend fun sortByArea(ascending: Boolean = true)
    {
        val newList = mCountries.value?.toMutableList()
        mCountries.value = withContext(coroutineContext) {
            newList?.apply {
                if(ascending)
                    sortBy { it._Area }
                else
                    sortByDescending { it._Area}
            }
        }
    }

    private fun getAllCountries()
    {
        retroRequestAsync(TAG,
                          CoroutineScope(Dispatchers.Main),
                          SMyRetrofit.countriesClient.getAllCountries(),
                          RetroCallback<List<Country>>().apply {
                              //for simplicity of this demo app, we treat all failures the same
                              onAnyFailure = { _, _, _, _ ->
                                  mCountries.value = null
                              }

                              onSuccess = { _, result, _ ->
                                  mCountries.value = result
                              }
                          })
    }
}