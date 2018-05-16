package com.winhtaikaung.devweekly.util

import android.arch.paging.PositionalDataSource
import android.support.annotation.NonNull
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.data.Source


object TestUtil {
    fun createArticle(id: String, name: String, img: String, content: String) = Article(
            id = id,
            objectId = id,
            img = img,
            title = name,
            preContent = content,
            articleViewContent = content,
            mainUrl = "url",
            issueId = id,
            sourceId = id,
            url = "http://",
            createdDate = "16253547",
            updatedDate = "82736473"

    )

    fun createIssue(id: String) = Issue(
            id = id,
            url = "http://",
            sourceId = "",
            objectId = id,
            issueNumber = "123456",
            updatedDate = "123456",
            createdDate = "456789")

    fun createSource(id: String) = Source(
            id = id,
            objectId = id,
            img = "http://",
            baseUrl = "http://",
            name = "Myanamr",
            tag = "Tag",
            createdDate = "123456",
            updatedDate = "123456"
    )

}

internal class ItemDataSource<T>(articleList: List<T>) : PositionalDataSource<T>() {
    var timetableDays: List<T> = articleList


    private fun computeCount(): Int {
        // actual count code here
        return timetableDays.size
    }

    private fun loadRangeInternal(startPosition: Int, loadCount: Int): List<T> {
        // actual load code here
        return this.timetableDays
    }

    override fun loadInitial(@NonNull params: PositionalDataSource.LoadInitialParams,
                             @NonNull callback: PositionalDataSource.LoadInitialCallback<T>) {
        val totalCount = computeCount()
        val position = computeInitialLoadPosition(params, totalCount)
        val loadSize = computeInitialLoadSize(params, position, totalCount)
        callback.onResult(loadRangeInternal(position, loadSize), position, totalCount)
    }

    override fun loadRange(@NonNull params: LoadRangeParams,
                           @NonNull callback: LoadRangeCallback<T>) {
        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize))
    }
}
