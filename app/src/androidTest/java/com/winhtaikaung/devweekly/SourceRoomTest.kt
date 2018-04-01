package com.winhtaikaung.devweekly


import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import com.winhtaikaung.devweekly.repository.data.Source
import com.winhtaikaung.devweekly.repository.db.AppDatabase
import com.winhtaikaung.devweekly.repository.db.SourceDao

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException
import java.util.Date
import java.util.UUID

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Function

@RunWith(AndroidJUnit4::class)
class SourceRoomTest {
    private var mSourceDao: SourceDao? = null
    private var mdb: AppDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mdb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mSourceDao = mdb!!.sourceDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mdb!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeSourceAndReadInList() {

        for (i in 0..4) {
            val user = Source(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Test" + i,
                    "http://imageUrl", "Android Weekly", "http://baseurl.com",
                    Date().toGMTString(), Date().toGMTString())
            mSourceDao!!.insertSource(user)
        }
        val sourceList = mSourceDao!!.getSources(4, 0)

        val byname = sourceList.blockingGet()

        assertThat(byname.size, equalTo(4))
    }
}
