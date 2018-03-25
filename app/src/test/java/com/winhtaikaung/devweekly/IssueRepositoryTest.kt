package com.winhtaikaung.devweekly

import com.nhaarman.mockito_kotlin.mock
import com.winhtaikaung.devweekly.repository.IssueRepository
import com.winhtaikaung.devweekly.repository.api.IssueApi
import com.winhtaikaung.devweekly.repository.data.Data
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.data.IssueListResponse
import com.winhtaikaung.devweekly.repository.data.Issues
import com.winhtaikaung.devweekly.repository.db.IssueDao
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class IssueRepositoryTest {
    lateinit var issueRepository: IssueRepository
    lateinit var issueApi: IssueApi
    lateinit var issueDao: IssueDao
    var issueListGraphql = "{\n" +
            "  issues(limit: " + 1 + ", page: " + 1 + ",sourceId:" + "61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF" + ") \n" +
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

    @Before
    fun setup() {
        issueApi = mock()
        issueDao = mock()
        `when`(issueDao.getIssues(1, 1, "61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF"))
                .thenReturn(Single.just(emptyList()))
        issueRepository = IssueRepository(issueApi, issueDao)


    }

    @Test
    fun test_emptyCache_OnIssueListApi_resutEmptyList() {
        `when`(issueApi.getIssueList(issueListGraphql)).thenReturn(Observable.just(IssueListResponse(
                data = Data(source = null, sources = null, article = null,
                        articles = null,
                        issue = null, issues = Issues(meta = null, data = emptyList<Issue>()))
        )))
        issueRepository.getIssueList(1, 1, "61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF").test()
                .assertValue { it.isEmpty() }

    }
}