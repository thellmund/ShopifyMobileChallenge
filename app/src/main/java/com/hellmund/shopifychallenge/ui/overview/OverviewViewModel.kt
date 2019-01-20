package com.hellmund.shopifychallenge.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hellmund.shopifychallenge.data.model.Collection
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
    data class Data(val collections: List<Collection>) : Result()
    object ShowError : Result()
    object HideError : Result()
}

class OverviewViewModel @Inject constructor(
    private val remoteRepository: CollectionsRemoteRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val refreshRelay = PublishRelay.create<Action>()

    private val _viewState = MutableLiveData<OverviewViewState>()
    val viewState: LiveData<OverviewViewState> = _viewState

    init {
        val initialViewState = OverviewViewState.initial()

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
            Action.Refresh -> fetchCollections()
        }
    }

    private fun fetchCollections(): Observable<Result> {
        return remoteRepository
            .fetchCollections()
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
        viewState: OverviewViewState,
        result: Result
    ): OverviewViewState {
        return when (result) {
            Result.ShowLoading -> viewState.toLoading()
            is Result.Data -> viewState.toData(result.collections)
            Result.ShowError -> viewState.toError()
            Result.HideError -> viewState.toData()
        }
    }

    private fun render(viewState: OverviewViewState) {
        _viewState.postValue(viewState)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}
