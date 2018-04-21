package com.winhtaikaung.devweekly.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.winhtaikaung.devweekly.repository.data.Issue
import io.reactivex.Single

@Dao
interface IssueDao {
    @Query("SELECT * FROM issues WHERE sourceId = (:sourceId) ORDER BY issueNumber DESC limit (:limit) offset (:offset)")
    fun getIssues(limit: Int, offset: Int, sourceId: String): Single<List<Issue>>

    @Query("SELECT * FROM issues WHERE objectId = (:issueId)")
    fun getIssue(issueId: String): Single<Issue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssue(issue: Issue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulkIssues(issueList: List<Issue>)
}

