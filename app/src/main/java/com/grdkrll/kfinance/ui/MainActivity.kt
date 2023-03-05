package com.grdkrll.kfinance.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grdkrll.kfinance.ui.screens.login.LoginScreen
import com.grdkrll.kfinance.NavDest
import com.grdkrll.kfinance.ui.screens.home.HomeScreen
import com.grdkrll.kfinance.ui.screens.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(
                navigationDispatcher = navigationDispatcher,
                lifecycleOwner = this
            )
        }
    }
}


@Composable
fun MainContent(
    navigationDispatcher: NavigationDispatcher,
    lifecycleOwner: LifecycleOwner
) {
    Surface(
        color = MaterialTheme.colorScheme.background
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
                val viewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(viewModel)
            }
            navigationDispatcher.navigationEmitter.observe(lifecycleOwner) { navigationCommand ->
                navigationCommand.invoke(navController)
            }
        }
    }
}