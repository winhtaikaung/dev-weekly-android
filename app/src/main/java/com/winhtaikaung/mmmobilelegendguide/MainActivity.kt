package com.winhtaikaung.mmmobilelegendguide

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity

open class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mDrawerLayout = findViewById(R.id.drawerLayout) as DrawerLayout

    }
}
