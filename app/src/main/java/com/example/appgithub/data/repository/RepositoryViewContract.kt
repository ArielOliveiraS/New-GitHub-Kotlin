package com.example.appgithub.data.repository

import com.example.appgithub.model.GitHubResponse
import com.example.appgithub.model.Repository
import io.reactivex.Single

interface RepositoryViewContract {
    fun getRepositories(page: Int): Single<GitHubResponse>
    fun getMyRepositories(user: String): Single<MutableList<Repository>>
}