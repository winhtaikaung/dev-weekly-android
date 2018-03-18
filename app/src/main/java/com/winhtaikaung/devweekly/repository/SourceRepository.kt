package com.winhtaikaung.devweekly.repository

import android.util.Log
import com.winhtaikaung.devweekly.repository.api.SourceApi
import com.winhtaikaung.devweekly.repository.data.SourceListResponse
import com.winhtaikaung.devweekly.repository.data.SourceResponse
import io.reactivex.Observable

class SourceRepository(val sourceApi: SourceApi) {

    fun getSourceList(limit:Int,page:Int):Observable<SourceListResponse>{
        return Observable.concatArray(getSourceListFromApi(limit,page))
    }

    fun getSourceListFromApi(limit: Int, page: Int): Observable<SourceListResponse> {
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
        return sourceApi.getSourceList(graphql).doOnNext {
            //TODO store source data in DB here
        }
    }

    fun getSourceFromApi(sourceId: String): Observable<SourceResponse> {
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
        return sourceApi.getSource(graphql).doOnNext {
            Log.e("SourceAPI", "Fetching ${it.data.source.toString()} from Api......")
            // TODO StoreSource in here
        }
    }
}
