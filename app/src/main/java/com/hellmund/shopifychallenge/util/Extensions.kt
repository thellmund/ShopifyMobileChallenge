package com.hellmund.shopifychallenge.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.hellmund.shopifychallenge.ui.shared.Navigator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner, Observer<T> { block(it) })
}

val AppCompatActivity.navigator: Navigator
    get() = Navigator(this)
