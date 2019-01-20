package com.hellmund.shopifychallenge.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.hellmund.shopifychallenge.App

val Context.app: App
    get() = applicationContext as App

val Fragment.injector: AppComponent
    get() = requireContext().app.appComponent
