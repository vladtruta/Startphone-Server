package com.vladtruta.di

import com.google.gson.Gson
import com.vladtruta.repository.ApplicationRepository
import com.vladtruta.repository.IAppRepo
import org.koin.dsl.module

val appModule = module {
    single { provideGson() }

    single<IAppRepo> { ApplicationRepository(get()) }
}

private fun provideGson(): Gson {
    return Gson()
}