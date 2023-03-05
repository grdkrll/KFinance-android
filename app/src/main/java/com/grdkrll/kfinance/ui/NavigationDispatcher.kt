package com.grdkrll.kfinance.ui

import androidx.navigation.NavController
import com.grdkrll.kfinance.SingleLiveEvent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Alias for navigation commands that user [NavController].
 */
typealias NavigationCommand = (NavController) -> Unit

@ActivityRetainedScoped
class NavigationDispatcher @Inject constructor() {

    val navigationEmitter: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()

    fun dispatchNavigationCommand(navigationCommand: NavigationCommand) {
        navigationEmitter.value = navigationCommand
    }

}