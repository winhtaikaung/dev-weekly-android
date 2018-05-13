package com.winhtaikaung.devweekly.article

import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.ArticleList
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ArticleListViewModel(val articleRepository: ArticleRepository) {
    fun getArticles(limit: Int, page: Int, sourceId: String): Observable<ArticleList> {
        return articleRepository.getArticleList(limit, page, sourceId)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    ArticleList(it, "${limit} issues")
                }
                .onErrorReturn {
                    ArticleList(emptyList(), "An error occured", it)
                }
    }


}