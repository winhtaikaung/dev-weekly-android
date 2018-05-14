package com.winhtaikaung.devweekly

import android.content.res.Configuration
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
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


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mNavigationView: NavigationView? = null
    lateinit var manager: FragmentManager
    lateinit var transaction: FragmentTransaction
    private var mContainer: FrameLayout? = null
    private var mToolbar: Toolbar? = null
    private var tmpImageView: ImageView? = null
    private var menuList: TreeMap<String, String>? = TreeMap<String, String>()

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
        mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.close_drawer)
        this.mDrawerLayout!!.setDrawerListener(mDrawerToggle)

        manager = supportFragmentManager
        transaction = manager.beginTransaction()



    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
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
        manager = supportFragmentManager
        transaction = manager.beginTransaction()
        mToolbar?.title = item.title
        transaction.replace(R.id.content_frame, IssueListFragement.newInstance(menuList?.get(item.title).toString())) // newInstance() is a static factory method.
        transaction.commit()
        item.isChecked = true
        // close drawer when item is tapped
        mDrawerLayout?.closeDrawers()

        return true
    }


}
