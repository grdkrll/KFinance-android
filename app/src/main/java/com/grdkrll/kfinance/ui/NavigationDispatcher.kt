package com.grdkrll.kfinance.ui

import androidx.navigation.NavController
import com.grdkrll.kfinance.SingleLiveEvent

/**
 * Alias for navigation commands that uses [NavController].
 */
typealias NavigationCommand = (NavController) -> Unit


/**
 * A Class for Navigating between different screens easily
 * @property navigationEmitter an Instance of [SingleLiveEvent] class that allows to emit navigation commands
 */
class NavigationDispatcher {

    val navigationEmitter: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()

    /**
     * Use to emit a navigation command
     * @param navigationCommand The command to be executed
     */
    fun dispatchNavigationCommand(navigationCommand: NavigationCommand) {
        navigationEmitter.value = navigationCommand
    }

}