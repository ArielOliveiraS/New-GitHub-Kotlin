package com.example.appgithub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appgithub.data.repository.ItemRepository
import com.example.appgithub.data.repository.RepositoryViewContract
import com.example.appgithub.model.GitHubResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by arieloliveira on 08/01/21 for AppGitHub.
 */
class GitHubViewModel (private val repository: RepositoryViewContract) : ViewModel() {
    private val repositoriesList: MutableLiveData<GitHubResponse> = MutableLiveData()

    val repositorytResult: LiveData<GitHubResponse> = repositoriesList

    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    val loadingResult: LiveData<Boolean> = loading

    private val genericError: MutableLiveData<Boolean> = MutableLiveData()
    val errorResult: LiveData<Boolean> = genericError

    fun getAllRepositories(page: Int) {
        repository.getRepositories(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe({
                setItemList(it)
                setError(false)
            }, { throwable ->
                Throwable(throwable)
                setError(true)
            })

    }

    fun setItemList(it: GitHubResponse?) {
        repositoriesList.value = it
    }

    fun setLoading(value: Boolean) {
        loading.value = value
    }

    fun setError(error: Boolean) {
        genericError.value = error
    }

    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GitHubViewModel(ItemRepository()) as T
        }
    }
}