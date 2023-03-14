package com.grdkrll.kfinance

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin
import com.grdkrll.kfinance.di.appModule
import org.koin.android.ext.koin.androidContext

class KFinanceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidContext(this@KFinanceApplication)
            modules(appModule)
        }
    }
}