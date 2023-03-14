package com.grdkrll.kfinance.di

import com.grdkrll.kfinance.remote.service.user.UserService
import com.grdkrll.kfinance.remote.service.user.impl.UserServiceImpl
import com.grdkrll.kfinance.repository.user.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.screens.home.HomeViewModel
import com.grdkrll.kfinance.ui.screens.login.LoginViewModel
import com.grdkrll.kfinance.ui.screens.pre_login.PreLoginViewModel
import com.grdkrll.kfinance.ui.screens.register.RegisterViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
    single { UserServiceImpl(get()) } bind UserService::class
}

val nabigationDispatcherModule = module {
    single { NavigationDispatcher() }
}

val registerViewModelModule = module {
    viewModel { RegisterViewModel(get(), get()) }
}

val homeViewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val loginViewModelModule = module {
    viewModel { LoginViewModel(get()) }
}

val preLoginViewModelModule = module {
    viewModel { PreLoginViewModel(get()) }
}

val appModule = module {
    single { UserRepository(get()) }
} + networkModule + nabigationDispatcherModule + registerViewModelModule + loginViewModelModule + preLoginViewModelModule + homeViewModelModule