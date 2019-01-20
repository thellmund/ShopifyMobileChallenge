package com.hellmund.shopifychallenge.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hellmund.shopifychallenge.ui.details.di.CollectionId
import com.hellmund.shopifychallenge.ui.details.viewmodel.ProductViewEntity
import com.hellmund.shopifychallenge.ui.details.viewmodel.ProductsViewEntityMapper
import com.hellmund.shopifychallenge.util.plusAssign
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed class Action {
    object Refresh : Action()
}

sealed class Result {
    object ShowLoading : Result()
    data class Data(val collections: List<ProductViewEntity>) : Result()
    object ShowError : Result()
    object HideError : Result()
}

class DetailsViewModel @Inject constructor(
    private val remoteRepository: ProductsRemoteRepository,
    private val viewEntityMapper: ProductsViewEntityMapper,
    @CollectionId private val collectionId: Long
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val refreshRelay = PublishRelay.create<Action>()

    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState> = _viewState

    init {
        val initialViewState = DetailsViewState.initial()

        compositeDisposable += refreshRelay
            .switchMap(this::processAction)
            .scan(initialViewState, this::reduceState)
            .subscribe(this::render)

        refreshRelay.accept(Action.Refresh)
    }

    fun refresh() {
        refreshRelay.accept(Action.Refresh)
    }

    private fun processAction(action: Action): Observable<Result> {
        return when (action) {
            Action.Refresh -> fetchProducts()
        }
    }

    private fun fetchProducts(): Observable<Result> {
        return remoteRepository
            .fetchProducts(collectionId)
            .map(viewEntityMapper)
            .map { Result.Data(it) as Result }
            .onErrorResumeNext { _: Throwable -> createSelfDismissingError() }
            .startWith(Result.ShowLoading)
    }

    private fun createSelfDismissingError(): Observable<Result> {
        return Observable.timer(4, TimeUnit.SECONDS)
            .map { Result.HideError as Result }
            .startWith(Result.ShowError)
    }

    private fun reduceState(
        viewState: DetailsViewState,
        result: Result
    ): DetailsViewState {
        return when (result) {
            Result.ShowLoading -> viewState.toLoading()
            is Result.Data -> viewState.toData(result.collections)
            Result.ShowError -> viewState.toError()
            Result.HideError -> viewState.toData()
        }
    }

    private fun render(viewState: DetailsViewState) {
        _viewState.postValue(viewState)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}
