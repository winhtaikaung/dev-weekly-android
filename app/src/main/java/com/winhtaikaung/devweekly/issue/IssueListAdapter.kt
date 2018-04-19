package com.winhtaikaung.devweekly.issue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.base.BaseAdapter
import com.winhtaikaung.devweekly.repository.data.Issue
import java.util.*

class IssueListAdapter : BaseAdapter<BaseAdapter.BaseViewHolder>() {

    internal var mContext: Context? = null
    internal var mIssueList: List<Issue>? = null

    init {
        mIssueList = ArrayList()
    }

    fun setmIssueList(issueList: List<Issue>) {
        this.mIssueList = issueList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.BaseViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_issue_list, parent, false)
        return IssueListViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: BaseAdapter.BaseViewHolder, position: Int) {
        val vh = holder as IssueListViewHolder
        if (mIssueList!![position] != null) {
            val (id, _, _, issueNumber) = mIssueList!![position]
            vh.tvIssueName.tag = id
            vh.tvIssueName.text = issueNumber

        }
    }

    override fun getItemCount(): Int {
        return if (mIssueList != null) mIssueList!!.size else 0
    }

    internal inner class IssueListViewHolder(itemView: View, issueListAdapter: IssueListAdapter) : BaseAdapter.BaseViewHolder(itemView) {

        var tvIssueName: TextView

        init {
            tvIssueName = itemView.findViewById(R.id.tvIssueName)
            itemView.setOnClickListener(this)
            mAdapter = issueListAdapter
        }
    }
}
