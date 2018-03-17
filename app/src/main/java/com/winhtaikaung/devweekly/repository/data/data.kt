package com.winhtaikaung.devweekly.repository.data


data class SourceResponse(
        val data: Data
)

data class IssueResponse(
        val data: Data
)

data class ArticleResponse(
        val data: Data
)

data class SourceListResponse(
        val data: Data
)

data class IssueListResponse(
        val data: Data
)

data class ArticleListResponse(
        val data: Data
)

data class Sources(
        val meta: Meta,
        val data: List<Source>
)


data class Articles(
        val meta: Meta,
        val data: List<Article>
)


data class Issues(
        val meta: Meta,
        val data: List<Issue>
)

data class Data(
        val source: Source,
        val issue: Issue,
        val article: Article,
        val issues: Issues,
        val articles: Articles,
        val sources: Sources
)


data class Meta(
        val totalPages: Int,
        val current: Int,
        val prevPage: Int,
        val nextPage: Int
)

/****************
 * Room Entities
 * ****************/
data class Article(
        val id: String,
        val objectId: String,
        val url: String,
        val img: String,
        val mainUrl: String,
        val title: String,
        val preContent: String,
        val issueId: String,
        val sourceId: String,
        val createdDate: String,
        val updatedDate: String
)

data class Issue(
        val id: String,
        val objectId: String,
        val url: String,
        val issueNumber: String,
        val sourceId: String,
        val createdDate: String,
        val updatedDate: String
)

data class Source(
        val id: String,
        val objectId: String,
        val tag: String,
        val img: String,
        val name: String,
        val baseUrl: String,
        val createdDate: String,
        val updatedDate: String
)
