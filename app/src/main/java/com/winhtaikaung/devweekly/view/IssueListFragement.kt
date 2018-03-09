package com.winhtaikaung.devweekly.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.winhtaikaung.devweekly.R

open class IssueListFragement : MvvmFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_issue_list, container, false)
        return view
    }
}