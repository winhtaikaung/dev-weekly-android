package com.winhtaikaung.devweekly

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.*
import com.winhtaikaung.devweekly.repository.db.ArticleDao
import com.winhtaikaung.devweekly.util.InstantAppExecutors
import com.winhtaikaung.devweekly.util.ItemDataSource
import com.winhtaikaung.devweekly.util.TestUtil
import com.winhtaikaung.devweekly.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class ArticleRepositoryTest {
    private val countryDao = Mockito.mock(ArticleDao::class.java)
    private val countryService = Mockito.mock(ArticleApi::class.java)
    private val dataSourceFactoryMock = Mockito.mock(DataSource.Factory::class.java)
    private val repo = ArticleRepository(InstantAppExecutors(), countryDao, countryService)
    var articleListGraphql = "{\n" +
            "  articles(limit: " + 1 + ", page: " + 1 + ",issueId:\"61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF\") {\n" +
            "    meta {\n" +
            "      totalPages\n" +
            "      current\n" +
            "      prevPage\n" +
            "      nextPage\n" +
            "    }\n" +
            "    data {\n" +
            "      id\n" +
            "      objectId\n" +
            "      url\n" +
            "      img\n" +
            "      mainUrl\n" +
            "      title\n" +
            "      preContent\n" +
            "      issueId\n" +
            "      sourceId\n" +
            "      createdDate\n" +
            "      updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}\n"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadCountries() {
        val mockCountry1 = TestUtil.createArticle("111", "123456", "9876543210","abc")
        val mockCountry2 = TestUtil.createArticle("111", "123456", "9876543210","def")
        val mockCountry3 = TestUtil.createArticle("111", "123456", "9876543210","ghi")
        val timetableDays: List<Article> = listOf(mockCountry1, mockCountry2, mockCountry3)
        var mockDataSource = object : DataSource.Factory<Int, Article>() {
            override fun create(): DataSource<Int, Article> {
               return  timetableDays as DataSource<Int, Article>
            }

        }

        `when`(countryDao.getArticles("123456")).thenReturn(mockDataSource)
        repo.loadArticleList("123456")
        verify(countryDao).getArticles("123456")
    }

    @Test
    fun loadCountriesFromNetwork(){
        val mockCountry1 = TestUtil.createArticle("111", "123456", "9876543210","abc")
        val mockCountry2 = TestUtil.createArticle("111", "123456", "9876543210","def")
        val mockCountry3 = TestUtil.createArticle("111", "123456", "9876543210","ghi")
        val timetableDays: List<Article> = listOf(mockCountry1, mockCountry2, mockCountry3)
        var mockDataSource = object : DataSource.Factory<Int, Article>() {
            override fun create(): DataSource<Int, Article> {
                return ItemDataSource(timetableDays)
            }

        }
        var query = ""
        Mockito.`when`(countryDao!!.getArticles("123456")).thenReturn(mockDataSource)

        val call = ArticleListResponse(data = Data(article = TestUtil.createArticle("abc","a.ppng","demo.png","lorem"),articles = Articles(meta = null,data = timetableDays),
                issue = TestUtil.createIssue("1"),issues = Issues(null, listOf(TestUtil.createIssue("a"))),source = TestUtil.createSource("1"),sources = Sources(null, listOf(TestUtil.createSource(("a"))))  ))
        Mockito.`when`(countryService!!.getArticleList(query)).thenReturn(ApiUtil.successCall(call))
        val observer = mock<Observer<Resource<PagedList<Article>>>>()

        repo.loadArticleList("123456").observeForever(observer)
        verify(countryService, Mockito.never()).getArticleList(query)


        var emptyMockSource = object : DataSource.Factory<Int, Article>() {
            override fun create(): DataSource<Int, Article>? {
                return  ItemDataSource(emptyList())
            }

        }


        Mockito.`when`(countryDao.getArticles("123456")).thenReturn(emptyMockSource)

        repo.loadArticleList("123456").observeForever(observer)
        verify(countryService).getArticleList(query)
    }

    @Test
    fun loadCountryOffline(){
        val dbData = MutableLiveData<List<Article>>()
        val mockCountry1 = TestUtil.createArticle("111", "123456", "9876543210","abc")
        val mockCountry2 = TestUtil.createArticle("111", "123456", "9876543210","def")
        val mockCountry3 = TestUtil.createArticle("111", "123456", "9876543210","ghi")
        val timetableDays: List<Article> = listOf(mockCountry1, mockCountry2, mockCountry3)

        var query = ""

        var mockDataSource = object : DataSource.Factory<Int, Article>() {
            override fun create(): DataSource<Int, Article> {
                return ItemDataSource(timetableDays)
            }

        }
        dbData.value = timetableDays
        `when`(countryDao!!.getArticles("123456")).thenReturn(mockDataSource)
        val observer = mock<Observer<Resource<PagedList<Article>>>>()
        repo.loadArticleList("123456").observeForever(observer)
        com.nhaarman.mockito_kotlin.verify(countryService, Mockito.never()).getArticleList(query)
        var pagedList =  PagedList.Builder(ItemDataSource(timetableDays),20).build()
        com.nhaarman.mockito_kotlin.verify(observer).onChanged(Resource.success(pagedList))
    }


}