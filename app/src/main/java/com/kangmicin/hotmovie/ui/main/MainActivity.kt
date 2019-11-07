package com.kangmicin.hotmovie.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.databinding.ActivityMainBinding
import com.kangmicin.hotmovie.ui.AppActivity
import com.kangmicin.hotmovie.ui.main.favorites.MovieFavoriteFragment
import com.kangmicin.hotmovie.ui.main.favorites.TvFavoriteFragment
import com.kangmicin.hotmovie.ui.main.movies.MoviesFragment
import com.kangmicin.hotmovie.ui.main.tvs.TvsFragment
import com.kangmicin.hotmovie.ui.setting.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity() {

    lateinit var binding: ActivityMainBinding

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.show_movie_menu -> {
                binding.toolbar.title = getString(R.string.movies)
                binding.tabs.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(main_fragment_container.id, MoviesFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.show_tvshow_menu -> {
                binding.toolbar.title = getString(R.string.tv_show)
                binding.tabs.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(main_fragment_container.id, TvsFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.show_favorite_menu -> {
                binding.toolbar.title = getString(R.string.favorite)
                binding.tabs.visibility = View.VISIBLE
                binding.tabs.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
                    override fun onTabReselected(p0: TabLayout.Tab?) {}

                    override fun onTabUnselected(p0: TabLayout.Tab?) {}

                    override fun onTabSelected(p0: TabLayout.Tab?) {
                        Log.i("favorite", p0?.tag.toString())
                        if (p0?.tag === "movies") {
                            supportFragmentManager.beginTransaction()
                                .replace(main_fragment_container.id, MovieFavoriteFragment())
                                .commit()
                        }

                        if (p0?.tag === "tvs") {
                            supportFragmentManager.beginTransaction()
                                .replace(main_fragment_container.id, TvFavoriteFragment())
                                .commit()
                        }
                    }
                })
                return@OnNavigationItemSelectedListener true
            }
        }
        false
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
        setSupportActionBar(binding.toolbar)
    }

    override fun onLanguageChange() {
        super.onLanguageChange()
        // reset on language changed
        binding.bottomNavigation.selectedItemId = bottom_navigation.selectedItemId
    }
}
