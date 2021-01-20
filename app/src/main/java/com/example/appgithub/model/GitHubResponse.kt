package com.example.appgithub.model

data class GitHubResponse(
    val total_count: Int,
    val incomplete_results:  Boolean,
    val items: ArrayList<Item>
)