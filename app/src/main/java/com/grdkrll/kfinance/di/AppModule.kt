package com.grdkrll.kfinance.di


import androidx.room.Room
import com.grdkrll.kfinance.model.database.GroupsDatabase
import com.grdkrll.kfinance.model.database.TransactionDatabase
import com.grdkrll.kfinance.repository.*
import com.grdkrll.kfinance.service.groups.GroupsService
import com.grdkrll.kfinance.service.groups.impl.GroupsServiceImpl
import com.grdkrll.kfinance.service.transaction.TransactionService
import com.grdkrll.kfinance.service.transaction.impl.TransactionServiceImpl
import com.grdkrll.kfinance.service.user.UserService
import com.grdkrll.kfinance.service.user.impl.UserServiceImpl
import com.grdkrll.kfinance.ui.NavigationDispatcher
import com.grdkrll.kfinance.ui.screens.add_group.AddGroupViewModel
import com.grdkrll.kfinance.ui.screens.add_transaction.AddTransactionViewModel
import com.grdkrll.kfinance.ui.screens.create_group.CreateGroupViewModel
import com.grdkrll.kfinance.ui.screens.groups.GroupsListViewModel
import com.grdkrll.kfinance.ui.screens.home.HomeViewModel
import com.grdkrll.kfinance.ui.screens.login.LoginViewModel
import com.grdkrll.kfinance.ui.screens.pre_login.PreLoginViewModel
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

val preLoginViewModelModule = module {
    viewModel { PreLoginViewModel(get()) }
}

val addTransactionViewModel = module {
    viewModel { AddTransactionViewModel(get(), get()) }
}
val groupsListViewModel = module {
    viewModel { GroupsListViewModel(get(), get()) }
}

val profileScreenViewModelModule = module {
    viewModel { ProfileScreenViewModel(get(), get()) }
}

val addGroupViewModelModule = module {
    viewModel { AddGroupViewModel(get(), get()) }
}

val createGroupViewModelModule = module {
    viewModel { CreateGroupViewModel(get(), get()) }
}

val appModule = module {
    single { TokenRepository(androidContext()) }
    single { UserRepository(get(), get(), androidContext()) }
    single { TransactionRepository(get(), get(), get()) }
    single { SortRepository(androidContext()) }
    single { GroupRepository(get(), get(), get()) }
} + networkModule + navigationDispatcherModule + registerViewModelModule + loginViewModelModule + preLoginViewModelModule + homeViewModelModule + addTransactionViewModel + transactionDatabaseModule + groupsListViewModel + profileScreenViewModelModule + addGroupViewModelModule + createGroupViewModelModule + groupsDatabaseModule