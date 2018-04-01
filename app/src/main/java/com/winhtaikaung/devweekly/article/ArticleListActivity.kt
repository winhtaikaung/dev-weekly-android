package com.winhtaikaung.devweekly.article

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.widget.FrameLayout
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.base.MvvmActivity
import kotlinx.android.synthetic.main.activity_main.view.*

class ArticleListActivity : MvvmActivity() {

    private var contentFrame: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO issue id has to parsed from uri
        supportFragmentManager?.beginTransaction()?.replace(R.id.content_frame,ArticleListFragment.newInstance("issueId"))

    }
}
