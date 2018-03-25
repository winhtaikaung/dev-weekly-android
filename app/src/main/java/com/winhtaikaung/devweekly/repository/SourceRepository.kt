package com.winhtaikaung.devweekly.repository

import com.winhtaikaung.devweekly.repository.api.SourceApi
import com.winhtaikaung.devweekly.repository.data.Source
import com.winhtaikaung.devweekly.repository.db.SourceDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SourceRepository(val sourceApi: SourceApi, val sourceDao: SourceDao) {

    fun getSourceList(limit: Int, page: Int): Observable<List<Source>> {
        return Observable.concatArray(
                getSourceListFromApi(limit, page),
                getSourceListFromDB(limit, page))
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
        return sourceApi.getSourceList(graphql).flatMap { (data) ->
            storesSourcesListinDB(data.sources!!.data!!)
            Observable.just(data.sources!!.data!!)
        }.onErrorReturn {
                    emptyList()
                }
    }

    fun getSourceListFromDB(limit: Int, page: Int): Observable<List<Source>> {
        return sourceDao.getSources(limit, page).filter { it.isNotEmpty() }
                .toObservable()
                .doOnNext {
                    //                    Log.e("DAO", "Dispatching ${it.size} sources from DB...")

                }
    }

    fun getSource(sourceId: String): Observable<Source> {
        return Observable.concat(
                getSourceFromDB(sourceId),
                getSourceFromApi(sourceId))
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

        return sourceApi.getSource(graphql).flatMap { (data) ->
            storeSourceinDB(data.source!!)
            Observable.just(data.source!!)
        }
    }

    fun getSourceFromDB(sourceId: String): Observable<Source> {
        return sourceDao.getSource(sourceId)
                .toObservable()
                .doOnNext {
                    //                    Log.e("DAO", "Dispatching ${it.toString()} source from DB...")

                }
    }

    fun storeSourceinDB(source: Source) {
        Observable.fromCallable { sourceDao.insertSource(source) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    //                    Log.e("DAO", "Saving ${source.toString()} into DB")
                }
    }

    fun storesSourcesListinDB(sources: List<Source>) {
        Observable.fromCallable { sourceDao.insertBulkSource(sources) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    //                    Log.e("DAO", "Saving ${sources.size} source into DB...")
                }
    }
}
