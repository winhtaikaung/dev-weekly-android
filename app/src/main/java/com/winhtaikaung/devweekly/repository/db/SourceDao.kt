package com.winhtaikaung.devweekly.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.winhtaikaung.devweekly.repository.data.Source
import io.reactivex.Single

@Dao
interface SourceDao {
    @Query("SELECT * FROM sources limit (:limit) offset (:offset)")
    fun getSources(limit: Int, offset: Int): Single<List<Source>>

    @Query("SELECT * FROM sources where objectId = (:sourceId)")
    fun getSource(sourceId: String): Single<Source>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulkSource(sourceList: List<Source>)
}