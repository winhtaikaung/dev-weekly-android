package com.winhtaikaung.devweekly.issue

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
import com.winhtaikaung.devweekly.article.ArticleListActivity
import com.winhtaikaung.devweekly.base.CommonListAdapter
import com.winhtaikaung.devweekly.base.EndlessRecyclerViewAdapter
import com.winhtaikaung.devweekly.base.MvvmFragment
import com.winhtaikaung.devweekly.repository.data.Issue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


private const val SOURCE_ID = "param1"

open class IssueListFragement : MvvmFragment(), AdapterView.OnItemClickListener, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {




    private var sourceId: String? = null
    private var limit: Int = 10
    lateinit var mIssueList: MutableList<Issue>
    private var mPageCounter: Int = 1
    lateinit var rvIssueList: RecyclerView
    lateinit var commonListAdapter: CommonListAdapter
    private lateinit var mEndlessRecyclerViewAdapter: EndlessRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sourceId = it.getString(SOURCE_ID)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                IssueListFragement().apply {
                    arguments = Bundle().apply {
                        putString(SOURCE_ID, param1)
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
//        subscribe(issueListViewModel.getIssues(limit, page, sourceId!!)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Log.e("ISSUE_SIZE", "ISSUE LIST${it.issues.size}")
//                    if (it.issues.size > 0) {
//                        if (mPageCounter == 1) {
//                            mIssueList = it.issues as MutableList<Issue>
//                        } else {
//                            mIssueList.addAll(it.issues as MutableList<Issue>)
//                        }
//                        commonListAdapter.setmIssueList(mIssueList)
//                        mEndlessRecyclerViewAdapter.onDataReady(true)
//                        mPageCounter++
//                    } else {
//                        mEndlessRecyclerViewAdapter.onDataReady(false)
//                    }
//                }, {
//                    Timber.w(it)
////                    mEndlessRecyclerViewAdapter.onDataReady(false);
//                }))
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


        val i: Intent = Intent(this.context, ArticleListActivity::class.java)
        i.data = Uri.parse(this.resources.getString(R.string.article_list_url_pattern) + "issue_id=" + mIssueList[p2].objectId + "&&issue_number=" + mIssueList[p2].issueNumber)

        this.context!!.startActivity(i)


    }


}