package com.grdkrll.kfinance

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin
import com.grdkrll.kfinance.di.appModule
import org.koin.android.ext.koin.androidContext

/**
 * The Holder of the Application. Used mainly to initiate Dependency Injection
 */
class KFinanceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    /**
     * Initiate Dependency Injection. Uses Koin Framework.
     * All the Modules are contained in the [appModule]
     */
    private fun initDI() {
        startKoin {
            androidContext(this@KFinanceApplication)
            modules(appModule)
        }
    }
}