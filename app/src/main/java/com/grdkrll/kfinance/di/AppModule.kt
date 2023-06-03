/**
 * This file is used for Koin Dependency Injection Framework
 */

package com.grdkrll.kfinance.di


import androidx.room.Room
import com.grdkrll.kfinance.model.database.GroupsDatabase
import com.grdkrll.kfinance.model.database.TransactionDatabase
import com.grdkrll.kfinance.repository.*
import com.grdkrll.kfinance.service.GroupsService
import com.grdkrll.kfinance.service.impl.GroupsServiceImpl
import com.grdkrll.kfinance.service.TransactionService
import com.grdkrll.kfinance.service.impl.TransactionServiceImpl
import com.grdkrll.kfinance.service.UserService
import com.grdkrll.kfinance.service.impl.UserServiceImpl
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.screens.join_group.JoinGroupViewModel
import com.grdkrll.kfinance.ui.screens.add_transaction.AddTransactionViewModel
import com.grdkrll.kfinance.ui.screens.all_transactions.AllTransactionsViewModel
import com.grdkrll.kfinance.ui.screens.create_group.CreateGroupViewModel
import com.grdkrll.kfinance.ui.screens.group_settings.GroupSettingsViewModel
import com.grdkrll.kfinance.ui.screens.groups_list.GroupsListViewModel
import com.grdkrll.kfinance.ui.screens.home.HomeViewModel
import com.grdkrll.kfinance.ui.screens.login.LoginViewModel
import com.grdkrll.kfinance.ui.screens.profile.ProfileScreenViewModel
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
                    sendWithoutRequest { true }
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
    single { GroupsServiceImpl(get()) } bind GroupsService::class
}

val transactionDatabaseModule = module {
    single {
        Room.databaseBuilder(get(), TransactionDatabase::class.java, "KFinanceDatabase").build()
    }
}

val groupsDatabaseModule = module {
    single {
        Room.databaseBuilder(get(), GroupsDatabase::class.java, "KFinanceDatabase").build()
    }
}

val navigationDispatcherModule = module {
    single { NavigationDispatcher() }
}

val registerViewModelModule = module {
    viewModel { RegisterViewModel(get(), get()) }
}

val homeViewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
}

val loginViewModelModule = module {

    viewModel {
        LoginViewModel(get(), get())
    }
}

val addTransactionViewModel = module {
    viewModel { AddTransactionViewModel(get(), get(), get(), get()) }
}
val groupsListViewModel = module {
    viewModel { GroupsListViewModel(get(), get(), get(), get(), get()) }
}

val profileScreenViewModelModule = module {
    viewModel { ProfileScreenViewModel(get(), get()) }
}

val joinGroupViewModelModule = module {
    viewModel { JoinGroupViewModel(get(), get()) }
}

val createGroupViewModelModule = module {
    viewModel { CreateGroupViewModel(get(), get()) }
}

val allTransactionsViewModelModule = module {
    viewModel { AllTransactionsViewModel(get(), get()) }
}

val groupSettingsViewModelModule = module {
    viewModel { GroupSettingsViewModel(get(), get(), get()) }
}

val appModule = module {
    single { TokenRepository(androidContext()) }
    single { UserRepository(get(), get(), androidContext(), get()) }
    single { TransactionRepository(get(), get(), get(), get()) }
    single { GroupRepository(get(), get(), get(), get()) }
    single { SelectedGroupRepository(androidContext()) }
    single { GroupSettingsRepository(androidContext()) }
} + networkModule + navigationDispatcherModule + registerViewModelModule + loginViewModelModule + homeViewModelModule + addTransactionViewModel + transactionDatabaseModule + groupsListViewModel + profileScreenViewModelModule + joinGroupViewModelModule + createGroupViewModelModule + groupsDatabaseModule + allTransactionsViewModelModule + groupSettingsViewModelModule