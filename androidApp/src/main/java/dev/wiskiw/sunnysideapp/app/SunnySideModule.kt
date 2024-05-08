package dev.wiskiw.sunnysideapp.app

import dev.wiskiw.sunnysideapp.presentation.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Should be declared above usage in includes(..)
// bug: https://github.com/InsertKoinIO/koin/issues/1341
private val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val androidModule = module {
    includes(viewModelModule)
}
