package com.example.appgithub.data.remote

import com.example.appgithub.model.GitHubResponse
import com.example.appgithub.model.Repository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories?q=language:kotlin&sort=stars&")
    fun getAllRepositories(@Query("page") page: Int): Single<GitHubResponse>

    @GET("https://api.github.com/users/{user}/repos")
    fun getMyRepositories(@Path("user", encoded = true) user: String): Single<MutableList<Repository>>
}