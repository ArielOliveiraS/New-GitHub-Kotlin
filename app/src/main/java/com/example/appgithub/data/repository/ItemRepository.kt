package com.example.appgithub.data.repository

import com.example.appgithub.data.remote.RetrofitService.Companion.service
import com.example.appgithub.model.GitHubResponse
import com.example.appgithub.model.Repository
import io.reactivex.Single

class ItemRepository : RepositoryViewContract {
    override fun getRepositories(page: Int): Single<GitHubResponse> = service.getAllRepositories(page)
    override fun getMyRepositories(user: String): Single<MutableList<Repository>> = service.getMyRepositories(user)
}