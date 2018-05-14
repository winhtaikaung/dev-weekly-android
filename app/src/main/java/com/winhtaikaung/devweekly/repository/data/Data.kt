package com.winhtaikaung.devweekly.repository.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


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
        val meta: Meta?,
        val data: List<Source>?
)


data class Articles(
        val meta: Meta?,
        val data: List<Article>
)


data class Issues(
        val meta: Meta?,
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
        val totalPages: Int?,
        val current: Int?,
        val prevPage: Int?,
        val nextPage: Int?
)

/****************
 * Room Entities
 * ****************/
@Entity(tableName = "articles")
data class Article(
        @ColumnInfo(name = "id")
        val id: String,
        @PrimaryKey
        @ColumnInfo(name = "objectId")
        val objectId: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "img")
        val img: String,
        @ColumnInfo(name = "mainUrl")
        val mainUrl: String,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "preContent")
        val preContent: String,
        @ColumnInfo(name = "articleViewContent")
        val articleViewContent: String,
        @ColumnInfo(name = "issueId")
        val issueId: String,
        @ColumnInfo(name = "sourceId")
        val sourceId: String,
        @ColumnInfo(name = "createdDate")
        val createdDate: String,
        @ColumnInfo(name = "updatedDate")
        val updatedDate: String
)

@Entity(tableName = "issues")
data class Issue(
        @ColumnInfo(name = "id")
        val id: String,
        @PrimaryKey
        @ColumnInfo(name = "objectId")
        val objectId: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "issueNumber")
        val issueNumber: String,
        @ColumnInfo(name = "sourceId")
        val sourceId: String,
        @ColumnInfo(name = "createdDate")
        val createdDate: String,
        @ColumnInfo(name = "updatedDate")
        val updatedDate: String
)

@Entity(tableName = "sources")
data class Source(
        @ColumnInfo(name = "id")
        val id: String,
        @PrimaryKey
        @ColumnInfo(name = "objectId")
        val objectId: String,
        @ColumnInfo(name = "tag")
        val tag: String,
        @ColumnInfo(name = "img")
        val img: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "baseUrl")
        val baseUrl: String,
        @ColumnInfo(name = "createdDate")
        val createdDate: String,
        @ColumnInfo(name = "updatedDate")
        val updatedDate: String
)
