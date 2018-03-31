package com.winhtaikaung.devweekly.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.winhtaikaung.devweekly.App
import com.winhtaikaung.devweekly.R
import com.winhtaikaung.devweekly.base.MvvmFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

private const val SOURCE_ID = "param1"

open class IssueListFragement : MvvmFragment() {

    val issueListViewModel = App.injectIssueListViewModel()


    private var sourceId: String? = null

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
        subscribe(issueListViewModel.getIssues(10, 0, sourceId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                    Timber.w(it)
                }))
        return view
    }

}