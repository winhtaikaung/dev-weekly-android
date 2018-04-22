package com.winhtaikaung.devweekly.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.base.MvvmActivity

class ArticleListActivity : MvvmActivity() {

    private var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)
        //TODO issue id has to parsed from uri
        var intent: Intent = intent!!

        var data: Uri = intent.data

        var issueId: String = data!!.getQueryParameter("issue_id")
        var issueNumber = data!!.getQueryParameter("issue_number")

        mToolbar = findViewById(R.id.toolBar)
        setSupportActionBar(mToolbar)
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle(issueNumber)


        supportFragmentManager?.beginTransaction()?.replace(R.id.content_frame, ArticleListFragment.newInstance(issueId, issueNumber))?.commit()

    }
}
