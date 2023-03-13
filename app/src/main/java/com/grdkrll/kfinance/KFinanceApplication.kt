package com.grdkrll.kfinance

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.core.context.GlobalContext.startKoin
import javax.inject.Inject
import com.grdkrll.kfinance.di.appModule

@HiltAndroidApp
class KFinanceApplication @Inject constructor() : Application() {
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