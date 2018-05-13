package com.winhtaikaung.devweekly

import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.data.Source

data class IssueList(val issues: List<Issue>, val message: String, val error: Throwable? = null)

data class SourceList(val sources: List<Source>, val message: String, val error: Throwable? = null)

data class ArticleList(val articles: List<Article>, val message: String, val error: Throwable? = null)

data class ArticleDetail(val articleDetail: List<Article>, val message: String, val error: Throwable? = null)