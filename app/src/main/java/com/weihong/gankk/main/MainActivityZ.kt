package com.weihong.gankk.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.weihong.gankk.R
import com.weihong.gankk.about.AboutActivityZ
import com.weihong.gankk.util.GanKKConstant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by weihong on 17-11-16.
 */
class MainActivityZ : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        switchFragment(GanKKConstant.GANK_TYPE_ALL)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.about) {
            startActivity(Intent(this, AboutActivityZ::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        var type = GanKKConstant.GANK_TYPE_ALL

        /**
         * 替代批量if else或者switch
         */
        when (id) {
            R.id.nav_all -> type = GanKKConstant.GANK_TYPE_ALL
            R.id.nav_android -> type = GanKKConstant.GANK_TYPE_ANDROID
            R.id.nav_ios -> type = GanKKConstant.GANK_TYPE_IOS
            R.id.nav_javascript -> type = GanKKConstant.GANK_TYPE_JAVASCRIPT
            R.id.nav_app -> type = GanKKConstant.GANK_TYPE_APPS
            R.id.nav_arrow -> type = GanKKConstant.GANK_TYPE_LOCATION_ARROW
            R.id.nav_mood -> type = GanKKConstant.GANK_TYPE_MOOD
            R.id.nav_video -> type = GanKKConstant.GANK_TYPE_COLLECTION_VIDEO
            R.id.nav_more -> type = GanKKConstant.GANK_TYPE_MORE
        }
        switchFragment(type)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private var fragmentManager: FragmentManager? = null
    private var currentFragmentTag: String? = null

    fun switchFragment(name: String) {
        if (currentFragmentTag != null && currentFragmentTag == name) {
            return
        }

        if (fragmentManager == null) {
            fragmentManager = supportFragmentManager
        }

        val ft = fragmentManager!!.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        val currentFragment = fragmentManager!!.findFragmentByTag(currentFragmentTag)
        if (currentFragment != null) {
            ft.hide(currentFragment)
        }

        var foundFragment: Fragment? = fragmentManager!!.findFragmentByTag(name)

        if (foundFragment == null) {
            foundFragment = MainFragment.newInstance(name)
        }

        if (foundFragment == null) {

        } else if (foundFragment.isAdded) {
            ft.show(foundFragment)
        } else {
            ft.add(R.id.container, foundFragment, name)
        }
        ft.commit()
        currentFragmentTag = name
    }

}