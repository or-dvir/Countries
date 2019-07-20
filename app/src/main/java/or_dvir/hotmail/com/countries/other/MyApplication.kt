package or_dvir.hotmail.com.countries.other

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class MyApplication: Application()
{
    companion object
    {
        lateinit var instance: MyApplication
        private set
    }

    override fun onCreate()
    {
        super.onCreate()
        instance = this

        if (LeakCanary.isInAnalyzerProcess(this))
            return

        LeakCanary.install(this)
    }
}