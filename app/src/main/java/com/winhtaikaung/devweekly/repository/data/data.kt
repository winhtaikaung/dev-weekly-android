package com.winhtaikaung.devweekly.repository.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "source")
data class Source(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "img")
        val img: String,
        @ColumnInfo(name = "baseUrl")
        val baseUrl: String,
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String)


@Entity(tableName = "issue",
        foreignKeys = arrayOf(ForeignKey(entity = Source::class,
                parentColumns = arrayOf("id"), childColumns = arrayOf("source_id"))))
data class Issue(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "source_id")
        val sourceId: String,
        @ColumnInfo(name = "baseUrl")
        val baseUrl: String,
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String)

@Entity(tableName = "article", foreignKeys = arrayOf(ForeignKey(entity = Issue::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("issue_id"))))
data class Article(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "img")
        val img: String,
        @ColumnInfo(name = "pre_content")
        val preContent: String,
        @ColumnInfo(name = "source_id")
        val sourceId: String,
        @ColumnInfo(name = "issue_id")
        val issueId: String,
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @ColumnInfo(name = "updated_at")
        val updatedAt: String)


