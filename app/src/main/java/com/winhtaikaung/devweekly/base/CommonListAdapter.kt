package com.winhtaikaung.devweekly.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.data.Issue
import java.util.*

class CommonListAdapter : BaseAdapter<BaseAdapter.BaseViewHolder>() {

    internal var mContext: Context? = null
    internal var mItemList: List<Any>? = null
    internal var ISSUE_VIEW: Int = 111
    internal var ARTICLE_VIEW: Int = 222

    init {
        mItemList = ArrayList()
    }

    fun setmIssueList(issueList: List<Any>) {
        this.mItemList = issueList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.BaseViewHolder {
        mContext = parent.context
        if (viewType == ISSUE_VIEW) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_issue_list, parent, false)
            return IssueListViewHolder(view, this)
        } else {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_article_list, parent, false)
            return ArticleListViewHolder(view, this)
        }
    }

    override fun onBindViewHolder(holder: BaseAdapter.BaseViewHolder, position: Int) {
        if (getItemViewType(position) == ISSUE_VIEW) {
            val vh = holder as IssueListViewHolder
            if (mItemList!![position] != null) {
                val (id, _, _, issueNumber) = mItemList!![position] as Issue
                vh.tvIssueName.tag = id
                vh.tvIssueName.text = issueNumber

            }
        } else {
            val vh = holder as ArticleListViewHolder
            if (mItemList!![position] != null) {
                val (id, _, _, _, _, _, preContent) = mItemList!![position] as Article
                vh.tvIssueName.tag = id
                vh.tvIssueName.text = preContent

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mItemList!![position] is Issue) {
            return ISSUE_VIEW
        } else {
            return ARTICLE_VIEW
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList != null) mItemList!!.size else 0
    }

    internal inner class IssueListViewHolder(itemView: View, commonListAdapter: CommonListAdapter) : BaseAdapter.BaseViewHolder(itemView) {

        var tvIssueName: TextView

        init {
            tvIssueName = itemView.findViewById(R.id.tvIssueName)
            itemView.setOnClickListener(this)
            mAdapter = commonListAdapter
        }
    }

    internal inner class ArticleListViewHolder(itemView: View, commonListAdapter: CommonListAdapter) : BaseAdapter.BaseViewHolder(itemView) {
        var tvIssueName: TextView

        init {
            tvIssueName = itemView.findViewById(R.id.tvIssueName)
            itemView.setOnClickListener(this)
            mAdapter = commonListAdapter
        }
    }
}
