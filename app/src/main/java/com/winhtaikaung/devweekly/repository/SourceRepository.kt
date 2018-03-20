package com.winhtaikaung.devweekly.repository

import com.winhtaikaung.devweekly.repository.api.SourceApi
import com.winhtaikaung.devweekly.repository.data.Source
import io.reactivex.Observable

class SourceRepository(val sourceApi: SourceApi) {

    fun getSourceList(limit: Int, page: Int): Observable<List<Source>> {
        return Observable.concatArray(getSourceListFromApi(limit, page))
    }

    fun getSourceListFromApi(limit: Int, page: Int): Observable<List<Source>> {
        val graphql = "{\n" +
                "  sources(limit: " + limit + ", page: " + page + ") {\n" +
                "    meta {\n" +
                "      totalPages\n" +
                "      current\n" +
                "      prevPage\n" +
                "      nextPage\n" +
                "    }\n" +
                "    data {\n" +
                "      id\n" +
                "      objectId\n" +
                "      tag\n" +
                "      img\n" +
                "      name\n" +
                "      baseUrl\n" +
                "      createdDate\n" +
                "      updatedDate\n" +
                "    }\n" +
                "  }\n" +
                "}\n"
        return sourceApi.getSourceList(graphql).flatMap { (data) -> Observable.just(data.sources!!.data!!) }
    }

    fun getSourceFromApi(sourceId: String): Observable<Source> {
        val graphql = "{\n" +
                "  source(sourceId: \"" + sourceId + "\") {\n" +
                "    id\n" +
                "    objectId\n" +
                "    tag\n" +
                "    img\n" +
                "    name\n" +
                "    baseUrl\n" +
                "    createdDate\n" +
                "    updatedDate\n" +
                "  }\n" +
                "}"

        return sourceApi.getSource(graphql).flatMap { (data) -> Observable.just(data.source!!) }
    }
}
