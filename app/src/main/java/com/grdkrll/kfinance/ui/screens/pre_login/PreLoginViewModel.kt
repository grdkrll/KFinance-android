package com.grdkrll.kfinance.ui.screens.pre_login

import androidx.lifecycle.ViewModel
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreLoginViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
): ViewModel() {

    fun redirectToLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.LOGIN)
        }
    }
}