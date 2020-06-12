package com.vladtruta.di

import com.google.gson.Gson
import org.koin.dsl.module

val appModule = module {
    single { provideGson() }
}

private fun provideGson(): Gson {
    return Gson()
}