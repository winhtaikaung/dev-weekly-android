package com.winhtaikaung.devweekly.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.winhtaikaung.devweekly.App
import com.winhtaikaung.devweekly.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

open class IssueListFragement : MvvmFragment() {

    val issueListViewModel = App.injectIssueListViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_issue_list, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        subscribe(issueListViewModel.getIssues(10, 1, "1b058d2f1dc64a479f28e873ab65cb75")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //                    Timber.d("Received UIModel with ${it.issues.size} users.")
                    Log.e("Success", "${it.issues.toString()}")
                }, {
                    Timber.w(it)
                }))
    }
}