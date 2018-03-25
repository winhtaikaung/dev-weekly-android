package com.winhtaikaung.devweekly.repository

import android.util.Log
import com.winhtaikaung.devweekly.repository.api.IssueApi
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.db.IssueDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class IssueRepository(val issueApi: IssueApi, val issueDao: IssueDao) {

    fun getIssueList(limit: Int, page: Int, sourceId: String): Observable<List<Issue>> {
        return Observable.concatArray(getIssueListFromApi(limit, page, sourceId),
                getIssueListFromDB(limit, page, sourceId))
    }

    fun getIssueListFromApi(limit: Int, page: Int, sourceId: String): Observable<List<Issue>> {
        val graphql = "{\n" +
                "  issues(limit: " + limit + ", page: " + page + ",sourceId:\"" + sourceId + "\"){ \n" +
                "      meta {\n" +
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

        return issueApi.getIssueList(graphql).flatMap { (data) ->
            storesIssueListinDB(data.issues!!.data!!)
            Observable.just(data.issues!!.data!!)
        }.onErrorReturn {
                    emptyList()
                }
    }

    fun getIssueListFromDB(limit: Int, page: Int, sourceId: String): Observable<List<Issue>> {
        return issueDao.getIssues(limit, page, sourceId).filter { it.isNotEmpty() }
                .toObservable()
                .doOnNext {
                    Log.e("DAO", "Dispatching ${it.size} issues from DB...")

                }
    }


    fun getIssue(issueId: String): Observable<Issue> {
        return Observable.concat(getIssueFromDB(issueId), getIssueFromApi(issueId));
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
        return issueApi.getIssue(graphql).flatMap { (data) ->
            storeIssueinDB(data.issue!!)
            Observable.just(data.issue!!)
        }

    }

    fun getIssueFromDB(issueId: String): Observable<Issue> {
        return issueDao.getIssue(issueId)
                .toObservable()
                .doOnNext {
                    //                    Log.e("DAO", "Dispatching ${it.toString()} issues from DB...")

                }
    }

    fun storeIssueinDB(issue: Issue) {
        Observable.fromCallable { issueDao.insertIssue(issue) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    //                    Log.e("DAO", "Saving ${issue.toString()} into DB")
                }
    }

    fun storesIssueListinDB(issues: List<Issue>) {
        Observable.fromCallable { issueDao.insertBulkIssues(issues) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    //                    Log.e("DAO", "Saving ${issues.size} issues into DB...")
                }
    }
}