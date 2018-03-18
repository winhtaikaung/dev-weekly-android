package com.winhtaikaung.devweekly

import com.nhaarman.mockito_kotlin.mock
import com.winhtaikaung.devweekly.repository.IssueRepository
import com.winhtaikaung.devweekly.repository.api.IssueApi
import org.junit.Before
import org.junit.Test

class IssueRepositoryTest {
    lateinit var issueRepository: IssueRepository
    lateinit var issueApi: IssueApi

    @Before
    fun setup() {
        issueApi = mock()
        issueRepository = IssueRepository(issueApi)


    }

    @Test
    fun test_emptyCache_OnIssueListApi_resutEmptyList() {


    }
}