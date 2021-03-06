package or_dvir.hotmail.com.countries.other

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hotmail.or_dvir.dxadapter.DxAdapter
import com.hotmail.or_dvir.dxadapter.DxHolder
import com.hotmail.or_dvir.dxadapter.onItemClickListener
import com.hotmail.or_dvir.dxadapter.onItemLongClickListener
import kotlinx.android.synthetic.main.list_item_country.view.*
import or_dvir.hotmail.com.countries.R
import or_dvir.hotmail.com.countries.model.Country
import kotlin.math.roundToInt

class AdapterCountries(var mItems: MutableList<Country>,
                       override val onItemClick: onItemClickListener<Country>?)
    : DxAdapter<Country, AdapterCountries.ViewHolder>()
{

    override val onItemLongClick: onItemLongClickListener<Country>? = null
    override fun createAdapterViewHolder(itemView: View, parent: ViewGroup, viewType: Int)
            = ViewHolder(itemView)
    override fun getOriginalAdapterItems() = mItems
    override fun getItemLayoutRes(parent: ViewGroup, viewType: Int) = R.layout.list_item_country

    override fun bindViewHolder(holder: ViewHolder, position: Int, item: Country)
    {
        holder.apply {
            tvNativeName.text = item._NativeName
            tvEnglishName.text = item._EnglishName
            tvArea.text = item._Area?.roundToInt().toString()
        }
    }

    override fun unbindViewHolder(holder: ViewHolder, position: Int, item: Country)
    {
        holder.apply {
            tvNativeName.text = ""
            tvEnglishName.text = ""
            tvArea.text = ""
        }
    }
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    class ViewHolder(itemView: View): DxHolder(itemView)
    {
        val tvNativeName: TextView = itemView.listItem_country_tv_nativeName
        val tvEnglishName: TextView = itemView.listItem_country_tv_englishName
        val tvArea: TextView = itemView.listItem_country_tv_area
    }
}