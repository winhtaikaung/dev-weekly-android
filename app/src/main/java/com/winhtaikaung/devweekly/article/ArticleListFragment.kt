package com.winhtaikaung.devweekly.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.winhtaikaung.devweekly.App
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.base.CommonListAdapter
import com.winhtaikaung.devweekly.base.EndlessRecyclerViewAdapter
import com.winhtaikaung.devweekly.base.MvvmFragment
import com.winhtaikaung.devweekly.repository.data.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

private const val ISSUE_ID = "issueId"

private const val ISSUE_NUMBER = "issueNumber"

open class ArticleListFragment : MvvmFragment(), EndlessRecyclerViewAdapter.RequestToLoadMoreListener, AdapterView.OnItemClickListener {

    val articleListViewModel = App.injectArticleListViewModel()


    private var issueId: String? = null
    private var issueNumber: String? = null

    private var limit: Int = 5
    lateinit var mArticleList: MutableList<Article>
    private var mPageCounter: Int = 1
    lateinit var rvIssueList: RecyclerView
    lateinit var commonListAdapter: CommonListAdapter
    private lateinit var mEndlessRecyclerViewAdapter: EndlessRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            issueId = it.getString(ISSUE_ID)

            issueNumber = it.getString(ISSUE_NUMBER)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param issueId Parameter 1.
         * @param issueNumber Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(issueId: String, issueNumber: String) =
                ArticleListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ISSUE_ID, issueId)

                        putString(ISSUE_NUMBER, issueNumber)
                    }
                }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_container_list, container, false)
        rvIssueList = view.findViewById(R.id.rvIssueList)
        commonListAdapter = CommonListAdapter()
        commonListAdapter.setOnItemClickListener(this)

        var layoutManager: LinearLayoutManager = LinearLayoutManager(this.activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIssueList.layoutManager = layoutManager

        mEndlessRecyclerViewAdapter = EndlessRecyclerViewAdapter(this.activity!!, commonListAdapter, this)
        rvIssueList.adapter = mEndlessRecyclerViewAdapter

        return view
    }

    override fun onLoadMoreRequested() {
        if (mPageCounter == 1) {
            loadIssues(1, limit)
        } else {
            loadIssues(mPageCounter, limit)
        }
    }


    fun loadIssues(page: Int, limit: Int) {
        subscribe(articleListViewModel.getArticles(limit, page, issueId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("ISSUE_SIZE", "ISSUE LIST${it.articles.size}")
                    if (it.articles.size > 0) {
                        if (mPageCounter == 1) {
                            mArticleList = it.articles as MutableList<Article>
                        } else {
                            mArticleList.addAll(it.articles as MutableList<Article>)
                        }
                        commonListAdapter.setmIssueList(mArticleList)
                        mEndlessRecyclerViewAdapter.onDataReady(true)
                        mPageCounter++
                    } else {
                        mEndlessRecyclerViewAdapter.onDataReady(false)
                    }
                }, {
                    Timber.w(it)
                    mEndlessRecyclerViewAdapter.onDataReady(false);
                }))
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val i: Intent = Intent(this.context, ArticleDetailActivity::class.java)
        i.data = Uri.parse(this.resources.getString(R.string.article_url_pattern) + "article_id=" + mArticleList[p2].objectId)

        this.context!!.startActivity(i)
    }


}
