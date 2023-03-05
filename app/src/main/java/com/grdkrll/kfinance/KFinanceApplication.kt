package com.grdkrll.kfinance

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KFinanceApplication @Inject constructor() : Application()