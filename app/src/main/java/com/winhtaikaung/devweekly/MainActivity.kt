package com.winhtaikaung.devweekly

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import com.winhtaikaung.devweekly.view.IssueListFragement


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mContainer: FrameLayout? = null
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawerLayout)
        mNavigationView = findViewById(R.id.nav_view)
        mToolbar = findViewById(R.id.toolBar)
        mContainer = findViewById(R.id.content_frame)
        mNavigationView?.setNavigationItemSelectedListener(this)
        this.setSupportActionBar(mToolbar)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu)


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

        when(item?.itemId){
            R.id.menu_android_weekly,R.id.menu_javascript_weekly,R.id.menu_react_weekly,R.id.menu_ruby_weekly ->{
                mToolbar?.title = item?.title
                transaction?.replace(R.id.content_frame, IssueListFragement()) // newInstance() is a static factory method.
                transaction?.commit()
            }

        }
        item.isChecked = true
        // close drawer when item is tapped
        mDrawerLayout?.closeDrawers()

        return true
    }


}
