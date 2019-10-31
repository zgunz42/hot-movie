package com.kangmicin.hotmovie.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.databinding.ActivityMainBinding
import com.kangmicin.hotmovie.ui.AppActivity
import com.kangmicin.hotmovie.ui.main.movies.MoviesFragment
import com.kangmicin.hotmovie.ui.main.tvs.TvsFragment
import com.kangmicin.hotmovie.ui.setting.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity() {

    lateinit var binding: ActivityMainBinding

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.show_movie_menu -> {
                binding.toolbar.title = "Best\'s Movies"
                supportFragmentManager.beginTransaction()
                    .replace(main_fragment_container.id, MoviesFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.show_tvshow_menu -> {
                binding.toolbar.title = "Best\'s Tvs"
                supportFragmentManager.beginTransaction()
                    .replace(main_fragment_container.id, TvsFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun appTitle(): String {
        return getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_movie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        binding.bottomNavigation.selectedItemId = bottom_navigation?.selectedItemId ?: R.id.show_movie_menu
    }

    override fun onLanguageChange() {
        super.onLanguageChange()
        // reset on language changed
        binding.bottomNavigation.selectedItemId = bottom_navigation.selectedItemId
    }
}
