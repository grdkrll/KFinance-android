package com.grdkrll.kfinance.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.ui.screens.join_group.JoinGroupScreen
import com.grdkrll.kfinance.ui.screens.add_transaction.AddTransactionScreen
import com.grdkrll.kfinance.ui.screens.all_transactions.AllTransactionsScreen
import com.grdkrll.kfinance.ui.screens.create_group.CreateGroupScreen
import com.grdkrll.kfinance.ui.screens.group_settings.GroupSettingsScreen
import com.grdkrll.kfinance.ui.screens.groups_list.GroupsListScreen
import com.grdkrll.kfinance.ui.screens.home.HomeScreen
import com.grdkrll.kfinance.ui.screens.login.LoginScreen
import com.grdkrll.kfinance.ui.screens.login.LoginViewModel
import com.grdkrll.kfinance.ui.screens.profile.ProfileScreen
import com.grdkrll.kfinance.ui.screens.register.RegisterScreen
import com.grdkrll.kfinance.ui.screens.register.RegisterViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

/**
 * The Main Activity of the app
 */
class MainActivity : ComponentActivity() {

    private val navigationDispatcher by inject<NavigationDispatcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainContent(
                    navigationDispatcher = navigationDispatcher,
                    lifecycleOwner = this
                )
            }
        }
    }

}

/**
 * The main composable function that controls all the navigation in the app
 *
 * @param navigationDispatcher an instance of [NavigationDispatcher]
 * @param lifecycleOwner allows [navigationDispatcher] to work
 */
@Composable
fun MainContent(
    navigationDispatcher: NavigationDispatcher,
    lifecycleOwner: LifecycleOwner
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = NavDest.HOME
        ) {
            composable(NavDest.HOME) {
                HomeScreen()
            }
            composable(NavDest.LOGIN) {
                val viewModel = koinViewModel<LoginViewModel>()
                LoginScreen(viewModel)
            }
            composable(NavDest.REGISTER) {
                val viewModel = koinViewModel<RegisterViewModel>()
                RegisterScreen(viewModel)
            }
            composable(NavDest.ADD_TRANSACTION) {
                AddTransactionScreen()
            }
            composable(NavDest.GROUPS_LIST) {
                GroupsListScreen()
            }
            composable(NavDest.PROFILE) {
                ProfileScreen()
            }
            composable(NavDest.JOIN_GROUP) {
                JoinGroupScreen()
            }
            composable(NavDest.CREATE_GROUP) {
                CreateGroupScreen()
            }
            composable(NavDest.ALL_TRANSACTIONS) {
                AllTransactionsScreen()
            }
            composable(NavDest.GROUP_SETTINGS) {
                GroupSettingsScreen()
            }
            navigationDispatcher.navigationEmitter.observe(lifecycleOwner) { navigationCommand ->
                navigationCommand.invoke(navController)
            }
        }
    }
}