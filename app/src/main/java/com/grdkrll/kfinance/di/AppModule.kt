package com.grdkrll.kfinance.di

import com.grdkrll.kfinance.repository.service.user.UserService
import com.grdkrll.kfinance.repository.service.user.impl.UserServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
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

val appModule = module {} + networkModule