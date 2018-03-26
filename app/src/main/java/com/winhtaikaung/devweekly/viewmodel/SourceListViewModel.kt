package com.winhtaikaung.devweekly.viewmodel

import com.winhtaikaung.devweekly.repository.SourceRepository
import com.winhtaikaung.devweekly.viewmodel.data.SourceList
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class SourceListViewModel(val sourceRepository: SourceRepository) {
    fun getSources(limit: Int, page: Int): Observable<SourceList> {
        return sourceRepository.getSourceList(limit, page)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    SourceList(it, "${limit} sources")
                }
                .onErrorReturn {
                    SourceList(emptyList(), "An error occured", it)
                }
    }
}
