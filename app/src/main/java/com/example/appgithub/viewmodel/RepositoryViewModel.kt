package com.example.appgithub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appgithub.data.repository.ItemRepository
import com.example.appgithub.data.repository.RepositoryViewContract
import com.example.appgithub.model.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepositoryViewModel(private val repository: RepositoryViewContract) : ViewModel() {
    private val repositoriesList: MutableLiveData<MutableList<Repository>> = MutableLiveData()

    var disposables = HashSet<Disposable>()

    val repositorytResult: LiveData<MutableList<Repository>> = repositoriesList

    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    val loadingResult: LiveData<Boolean> = loading

    fun getMyRepositories(user: String) {
        repository.getMyRepositories(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe({
                repositoriesList.value = it
            }, { throwable ->
                Throwable(throwable)
                Log.i("erro", "meus repositorios erro")
            }).run { disposables.add(this) }

    }

    fun setLoading(value: Boolean) {
        loading.value = value
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoryViewModel(ItemRepository()) as T
        }
    }
}