package or_dvir.hotmail.com.countries.vvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import or_dvir.hotmail.com.countries.model.Country
import or_dvir.hotmail.com.countries.other.SMyRetrofit
import or_dvir.hotmail.com.dxutils.RetroCallback
import or_dvir.hotmail.com.dxutils.retroRequestAsync
import kotlin.coroutines.coroutineContext

class ActivityBordersViewModel(app: Application): AndroidViewModel(app)
{
    //it's kind of unnecessary to have this class only to hold 1 variable but i made it just to make
    //sure that the list of borders is kept "safe" during all stages of the activity life cycle

    val mBorders = MutableLiveData<List<Country>>()
}