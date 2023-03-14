package com.grdkrll.kfinance.ui.screens.home

import androidx.lifecycle.ViewModel
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.repository.user.UserRepository

class HomeViewModel(
    private val navigationDispatcher: NavigationDispatcher,
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUser(): User? = userRepository.getUser()

    fun redirectToPreLogin() {
        navigationDispatcher.dispatchNavigationCommand { navController ->
            navController.popBackStack()
            navController.navigate(NavDest.PRE_LOGIN)
        }
    }
}