package com.example.android.githubsearchwithsettings.data

import android.text.TextUtils
import com.example.android.githubsearchwithsettings.api.GitHubService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class GitHubReposRepository(
    private val service: GitHubService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearch(
        query: String,
        sort: String?,
        user: String?,
        languages: Set<String>?,
        firstIssues: Int
    ): Result<List<GitHubRepo>> =
        withContext(ioDispatcher) {
            try {
                val results = service.searchRepositories(
                    buildGitHubQuery(query, user, languages, firstIssues),
                    sort
                )
                Result.success(results.items)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private fun buildGitHubQuery(
        query: String,
        user: String?,
        languages: Set<String>?,
        firstIssues: Int
    ) : String {
        /*
         * e.g. "android user:square language:kotlin language:java good-first-issues:>=2"
         */
        var fullQuery = query
        if (!TextUtils.isEmpty(user)) {
            fullQuery += " user:$user"
        }
        if (languages != null && languages.isNotEmpty()) {
            fullQuery += languages.joinToString(separator = " language:", prefix = "language:")
        }
        if (firstIssues > 0) {
            fullQuery += " good-first-issues:>=$firstIssues"
        }
        return fullQuery
    }
}