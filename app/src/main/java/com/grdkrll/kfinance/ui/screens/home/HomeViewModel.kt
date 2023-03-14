package com.grdkrll.kfinance.ui.screens.home

import androidx.lifecycle.ViewModel
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.User
class HomeViewModel(
    private val navigationDispatcher: NavigationDispatcher
): ViewModel() {

    fun getUser() : User? {
        return null
    }

    fun redirectToPreLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PRE_LOGIN)
        }
    }
}