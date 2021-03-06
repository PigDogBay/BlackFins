package com.pigdogbay.blackfins

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.pigdogbay.blackfins.model.Injector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Injector.create(this)
        applySettings()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState==null){
            showConnection()
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_view.setNavigationItemSelectedListener(this)
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_connection -> showConnection()
            R.id.nav_live_data -> showLiveData()
            R.id.nav_chart -> showChart()
            R.id.nav_error_log -> showErrorLog()
            R.id.nav_settings -> showSettings()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showConnection(){
        title="Connection"
        replaceMainFragment(ConnectionFragment(),ConnectionFragment.TAG)
    }
    private fun showLiveData(){
        title="Live Data"
        replaceMainFragment(LiveDataFragment(),LiveDataFragment.TAG)
    }
    private fun showChart(){
        title="Data Log"
        replaceMainFragment(ChartFragment(),ChartFragment.TAG)
    }
    private fun showErrorLog(){
        title="Error Log"
        replaceMainFragment(ErrorLogFragment(),ErrorLogFragment.TAG)
    }
    private fun showSettings(){
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun replaceMainFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit()
    }

    private fun applySettings(){
        try {
            Injector.applySettings()
        } catch (e : Exception){
            Injector.settings.resetToDefault()
            Injector.applySettings()
            AlertDialog.Builder(this)
                    .setTitle("Settings Error")
                    .setMessage("Settings are badly formatted, resetting them")
                    .setNeutralButton("OK",null)
                    .show()
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        applySettings()
    }
}
