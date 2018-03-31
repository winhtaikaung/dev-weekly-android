package com.winhtaikaung.devweekly

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageView
import com.winhtaikaung.devweekly.base.MvvmActivity
import com.winhtaikaung.devweekly.issue.IssueListFragement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*


class MainActivity : MvvmActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mContainer: FrameLayout? = null
    private var mToolbar: Toolbar? = null
    private var sourceListViewModel = App.injectSourceListViewModel()
    private var tmpImageView: ImageView? = null
    private var menuList:TreeMap<String,String>? = TreeMap<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawerLayout)
        mNavigationView = findViewById(R.id.nav_view)
        mToolbar = findViewById(R.id.toolBar)
        mContainer = findViewById(R.id.content_frame)
        tmpImageView = findViewById(R.id.tempImg)
        mNavigationView?.setNavigationItemSelectedListener(this)
        this.setSupportActionBar(mToolbar)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        subscribe(sourceListViewModel.getSources(10, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var i = 0
                    it.sources.map {

                        mNavigationView?.menu?.add(0, i,i , it.name)
                        menuList!!.put(it.name,it.objectId)
                        i++
                        //TODO set icon dynamicaly or from assets
                    }
                    i = 0
                }, {
                    Timber.w(it)
                }))


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                mDrawerLayout?.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val manager = supportFragmentManager
        val transaction = manager?.beginTransaction()
        mToolbar?.title = item?.title
        transaction?.replace(R.id.content_frame, IssueListFragement.newInstance(menuList?.get(item.title).toString())) // newInstance() is a static factory method.
        transaction?.commit()
        item.isChecked = true
        // close drawer when item is tapped
        mDrawerLayout?.closeDrawers()

        return true
    }


}
