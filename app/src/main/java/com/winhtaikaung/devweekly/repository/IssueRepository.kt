package com.winhtaikaung.devweekly.repository

import android.util.Log
import com.winhtaikaung.devweekly.repository.api.IssueApi
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.data.IssueListResponse
import com.winhtaikaung.devweekly.repository.data.IssueResponse
import io.reactivex.Observable


class IssueRepository(val issueApi: IssueApi) {
    fun getIssueListFromApi(limit: Int, page: Int): Observable<List<Issue>> {
        val graphql = "{\n" +
                "  issues(limit: " + limit + ", page: " + page + ") {\n" +
                "    meta {\n" +
                "      totalPages\n" +
                "      current\n" +
                "      prevPage\n" +
                "      nextPage\n" +
                "    }\n" +
                "    data {\n" +
                "      id\n" +
                "      objectId\n" +
                "      url\n" +
                "      issueNumber\n" +
                "      sourceId\n" +
                "      createdDate\n" +
                "      updatedDate\n" +
                "    }\n" +
                "  }\n" +
                "}\n"

        return issueApi.getIssueList(graphql).flatMap { (data) -> Observable.just(data.issues!!.data!!) }
    }

    fun getIssueFromApi(issueId: String): Observable<Issue> {
        val graphql = "{\n" +
                "  issue(issueId:\"" + issueId + "\"){\n" +
                "    id\n" +
                "    objectId\n" +
                "    url\n" +
                "    issueNumber\n" +
                "    sourceId\n" +
                "    createdDate\n" +
                "    updatedDate\n" +
                "  }\n" +
                "}"
        return issueApi.getIssue(graphql).flatMap{(data)->Observable.just(data.issue!!)}

    }
}