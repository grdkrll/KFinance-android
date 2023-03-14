package com.grdkrll.kfinance.ui

import androidx.navigation.NavController
import com.grdkrll.kfinance.SingleLiveEvent

/**
 * Alias for navigation commands that user [NavController].
 */
typealias NavigationCommand = (NavController) -> Unit

class NavigationDispatcher {

    val navigationEmitter: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()

    fun dispatchNavigationCommand(navigationCommand: NavigationCommand) {
        navigationEmitter.value = navigationCommand
    }

}