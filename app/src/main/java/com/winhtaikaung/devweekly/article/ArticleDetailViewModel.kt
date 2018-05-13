package com.winhtaikaung.devweekly.article

import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.ArticleDetail
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ArticleDetailViewModel(val articleRepository: ArticleRepository) {
    fun getArticle(articleId: String): Observable<ArticleDetail> {
        return articleRepository.getArticle(articleId)
                .debounce(400, TimeUnit.MILLISECONDS).map {
            ArticleDetail(it, "${it.size}")
        }.onErrorReturn {
                    ArticleDetail(emptyList(), "Empty Article ", it)
                }

    }
}