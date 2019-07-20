package or_dvir.hotmail.com.countries.vvm

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_borders.*
import or_dvir.hotmail.com.countries.R
import or_dvir.hotmail.com.countries.model.Country
import or_dvir.hotmail.com.countries.other.AdapterCountries
import or_dvir.hotmail.com.countries.other.MyMoshi
import or_dvir.hotmail.com.dxutils.setHomeUpEnabled
import or_dvir.hotmail.com.dxutils.showSimpleDialog

class ActivityBorders : AppCompatActivity()
{
    companion object
    {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_BORDERS = "EXTRA_BORDERS"
    }

    private lateinit var mAdapter: AdapterCountries
    private lateinit var mViewModel: ActivityBordersViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borders)
        setHomeUpEnabled(true)

        intent?.apply {
            title = "${getStringExtra(EXTRA_TITLE)} ${getString(R.string.borders)}"

            mViewModel = ViewModelProviders.of(this@ActivityBorders)
                .get(ActivityBordersViewModel::class.java)

            mViewModel.apply {
                mBorders.value = mutableListOf<Country>().apply {
                    getStringArrayListExtra(EXTRA_BORDERS).forEach {
                        MyMoshi.fromJson(it)?.let { c -> add(c) }
                    }
                }

                //i am setting the value right above this line so i can be sure its not null
                mAdapter = AdapterCountries(mBorders.value!!.toMutableList(), null)

                mBorders.observe(this@ActivityBorders, Observer {
                    if(it.isEmpty())
                        showSimpleDialog(R.string.noBorders, R.string.ok)

                    mAdapter.mItems = it.toMutableList()
                    mAdapter.notifyDataSetChanged()
                })
            }
        }
            //if for some reason the intent is null
            ?: showSimpleDialog(R.string.error, R.string.ok, onClickListener = { onBackPressed() })

        activityBorders_rv.apply {
            addItemDecoration(DividerItemDecoration(this@ActivityBorders, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@ActivityBorders, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return if (item.itemId == android.R.id.home)
        {
            onBackPressed()
            true
        }
        else
            super.onOptionsItemSelected(item)
    }
}
