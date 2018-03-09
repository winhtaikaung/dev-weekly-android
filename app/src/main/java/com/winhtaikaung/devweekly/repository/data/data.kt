package com.winhtaikaung.devweekly.repository.data



data class SourceResponse(
		val data: Data
)

data class Data(
		val source: Source,
		val issue: Issue,
		val article: Article
)

data class IssueResponse(
		val data: Data
)
data class ArticleResponse(
		val data: Data
)


data class Article(
		val id: String,
		val objectId: String,
		val url: String,
		val img: String,
		val preContent: String,
		val sourceId: String,
		val issueId: String,
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
		val img: String,
		val baseUrl: String,
		val createdDate: String,
		val updatedDate: String
)
