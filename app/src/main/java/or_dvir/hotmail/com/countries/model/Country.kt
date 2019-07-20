package or_dvir.hotmail.com.countries.model

import androidx.recyclerview.widget.DiffUtil
import com.hotmail.or_dvir.dxadapter.interfaces.IItemBase
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//for the sake of this demo app i assume all values (other than area) are non-null
//todo make sure this is true
@JsonClass(generateAdapter = true)
data class Country(@Json(name = "area")
                   val _Area: Float?,
                   @Json(name = "borders")
                   val _Borders: List<String>,
                   @Json(name = "name")
                   val _EnglishName: String,
                   @Json(name = "nativeName")
                   val _NativeName: String,
                   @Json(name = "alpha3Code")
                   val _Alpha3Code: String)
    : IItemBase
{
    //this is a demo app and also this is the only model we will be showing in any lists,
    //therefore any number will be good here.
    //in a real app, we'd have to make sure each model has a unique id (type)
    override fun getViewType() = 1

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////

    class DiffCallback(private val oldList: MutableList<Country>,
                       private val newList: MutableList<Country>)
        : DiffUtil.Callback()
    {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].equals(newList[newItemPosition])

        //in this case, the contents of a country will never change so we can simply return TRUE
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    }
}