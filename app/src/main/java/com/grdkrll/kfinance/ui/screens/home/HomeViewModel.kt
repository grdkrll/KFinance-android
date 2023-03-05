package com.grdkrll.kfinance.ui.screens.home

import androidx.lifecycle.ViewModel
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
): ViewModel() {

    fun getUser() : User? {
        return null
    }

    fun redirectToLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.LOGIN)
        }
    }
}