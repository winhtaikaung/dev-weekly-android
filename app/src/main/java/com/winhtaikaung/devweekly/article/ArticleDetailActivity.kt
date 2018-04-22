package com.winhtaikaung.devweekly.article

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.winhtaikaung.devweekly.App
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.base.MvvmActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleDetailActivity : MvvmActivity() {
    private var articleDetailViewModel = App.injectArticleDetailViewModel()
    private var articleWebView: WebView? = null
    private var mToolbar: Toolbar? = null
    lateinit var actionBar: ActionBar
    lateinit var articleId: String
    lateinit var html: String
    lateinit var headerStr: String
    lateinit var footerStr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        var data: Uri = intent.data

        articleId = data!!.getQueryParameter("article_id")

        articleWebView = findViewById(R.id.articleWebView)
        mToolbar = findViewById(R.id.toolBar)
        setSupportActionBar(mToolbar)
        actionBar = this!!.supportActionBar!!
        actionBar?.setDisplayHomeAsUpEnabled(true)


        val articleHeaderTemplate = assets.open("article_header.html")


        val headerSize = articleHeaderTemplate.available()


        val buffer = ByteArray(headerSize)

        articleHeaderTemplate.read(buffer)
        articleHeaderTemplate.close()


        val articleFooterTemplate = assets.open("article_footer.html")
        val footerSize = articleFooterTemplate.available()
        val footerBuffer = ByteArray(footerSize)
        articleFooterTemplate.read(footerBuffer)
        articleFooterTemplate.close()

        headerStr = String(buffer)
        footerStr = String(footerBuffer)
        articleWebView?.webViewClient = WebViewClient()
        articleWebView?.webChromeClient = WebChromeClient()
        articleWebView?.settings?.javaScriptEnabled = true


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        subscribe(articleDetailViewModel.getArticle(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("DETAIL", "${it.articleDetail[0]}")
                    actionBar.title = it.articleDetail[0].title
                    html = it.articleDetail[0].articleViewContent!!
                    articleWebView?.loadData(headerStr + html + footerStr, "text/html; charset=utf-8", "UTF-8")
                }))
    }

}
