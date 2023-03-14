package com.grdkrll.kfinance

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin
import com.grdkrll.kfinance.di.appModule

class KFinanceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            modules(appModule)
        }
    }
}