package or_dvir.hotmail.com.countries.vvm

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import or_dvir.hotmail.com.countries.R
import or_dvir.hotmail.com.countries.model.Country
import or_dvir.hotmail.com.countries.other.AdapterCountries
import or_dvir.hotmail.com.dxutils.makeGone
import or_dvir.hotmail.com.dxutils.makeVisible
import or_dvir.hotmail.com.dxutils.showSimpleDialog
import org.jetbrains.anko.intentFor

class ActivityMain : AppCompatActivity()
{
    private lateinit var mAdapter: AdapterCountries
    private lateinit var mViewModel: ActivityMainViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //viewModel immediately sends request so show the dialog
        activityMain_rl_loading.makeVisible()
        mViewModel = ViewModelProviders.of(this).get(ActivityMainViewModel::class.java)

        //initialize with empty list until we get result from server
        mAdapter = AdapterCountries(mutableListOf()) { _, _, item ->
            startActivity(intentFor<ActivityBorders>(
                ActivityBorders.EXTRA_TITLE to item._EnglishName,
                ActivityBorders.EXTRA_BORDERS to mViewModel.getBordersAsJsonList(item)
                ))
        }

        mViewModel.mCountries.observe(this, Observer {
            activityMain_rl_loading.makeGone()

            if(it == null)
            {
                showSimpleDialog(R.string.error, R.string.ok)
                return@Observer
            }

            //for the sake of this demo app, i am assuming the list is not empty

            mAdapter.apply {
                //since we have sort option, we need to detect moves
                val result =
                    DiffUtil.calculateDiff(Country.DiffCallback(mItems, it.toMutableList()), true)

                mItems.apply {
                    clear()
                    addAll(it)
                }

                result.dispatchUpdatesTo(this)

                //make sure the list is at the top after sorting
                activityMain_rv.apply { post { scrollToPosition(0) } }
            }
        })

        activityMain_rv.apply {
            addItemDecoration(DividerItemDecoration(this@ActivityMain, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@ActivityMain, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        var temp = true

        //sorting the items takes some time
        activityMain_rl_loading.makeVisible()

        //for some reason sometimes when sorting by name (ascending)
        //the activityMain_rl_loading is not shown
        activityMain_rl_loading.post {
            CoroutineScope(Dispatchers.Main).launch {
                when (item.itemId)
                {
                    R.id.menu_sort_name_asc -> mViewModel.sortByName()
                    R.id.menu_sort_name_desc -> mViewModel.sortByName(false)
                    R.id.menu_sort_area_asc -> mViewModel.sortByArea()
                    R.id.menu_sort_area_desc -> mViewModel.sortByArea(false)
                    else -> { temp = super.onOptionsItemSelected(item) }
                }
            }
        }
        return temp
    }
}
