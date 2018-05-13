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
            "  issues(limit: " + 1 + ", page: " + 1 + ",sourceId:\"61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF\"){ \n" +
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


}