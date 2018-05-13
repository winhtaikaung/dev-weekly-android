package com.winhtaikaung.devweekly.issue

import com.winhtaikaung.devweekly.repository.IssueRepository
import com.winhtaikaung.devweekly.IssueList
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class IssueListViewModel(val issueRepository: IssueRepository) {
    fun getIssues(limit: Int, page: Int, sourceId: String): Observable<IssueList> {
        return issueRepository.getIssueList(limit, page, sourceId)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    IssueList(it, "${limit} issues")
                }
                .onErrorReturn {
                    IssueList(emptyList(), "An error occured", it)
                }
    }
}
