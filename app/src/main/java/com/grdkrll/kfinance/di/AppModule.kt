package com.grdkrll.kfinance.di


import androidx.room.Room
import com.grdkrll.kfinance.model.database.TransactionDatabase
import com.grdkrll.kfinance.remote.service.transaction.TransactionService
import com.grdkrll.kfinance.remote.service.transaction.impl.TransactionServiceImpl
import com.grdkrll.kfinance.remote.service.user.UserService
import com.grdkrll.kfinance.remote.service.user.impl.UserServiceImpl
import com.grdkrll.kfinance.repository.TransactionRepository
import com.grdkrll.kfinance.repository.TokenRepository
import com.grdkrll.kfinance.repository.UserRepository
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.screens.add_transaction.AddTransactionViewModel
import com.grdkrll.kfinance.ui.screens.home.HomeViewModel
import com.grdkrll.kfinance.ui.screens.login.LoginViewModel
import com.grdkrll.kfinance.ui.screens.pre_login.PreLoginViewModel
import com.grdkrll.kfinance.ui.screens.register.RegisterViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(Auth) {
                bearer {
                    val tokenRepository: TokenRepository = get()
                    loadTokens {
                        BearerTokens(tokenRepository.fetchAuthToken() ?: "", "")
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
    single { UserServiceImpl(get()) } bind UserService::class
    single { TransactionServiceImpl(get()) } bind TransactionService::class
}

val transactionDatabaseModule = module {
    single { Room.databaseBuilder(get(), TransactionDatabase::class.java, "KFinanceDatabase").build() }
}

val navigationDispatcherModule = module {
    single { NavigationDispatcher() }
}

val registerViewModelModule = module {
    viewModel { RegisterViewModel(get(), get()) }
}

val homeViewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}

val loginViewModelModule = module {

    viewModel {
        LoginViewModel(get(), get()) }
}

val preLoginViewModelModule = module {
    viewModel { PreLoginViewModel(get()) }
}

val addTransactionViewModel = module {
    viewModel { AddTransactionViewModel(get(), get()) }
}

val appModule = module {
    single { TokenRepository(androidContext()) }
    single { UserRepository(get(), get(), androidContext()) }
    single { TransactionRepository(get(), get(), get()) }
} + networkModule + navigationDispatcherModule + registerViewModelModule + loginViewModelModule + preLoginViewModelModule + homeViewModelModule + addTransactionViewModel + transactionDatabaseModule