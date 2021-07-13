package com.example.nasajm.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.nasajm.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_pages.*

private const val EARTH = 0
private const val MARS = 1
private const val MOON = 2

class PagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pages)

        val viewPagerAdapter = ViewPagerAdapter(this)

        view_pager.adapter = viewPagerAdapter
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = viewPagerAdapter.getFragmentName(position)
        }.attach()

        setSelectedTab(EARTH)
    }

    private fun setSelectedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@PagesActivity)

        tab_layout.getTabAt(EARTH)?.customView = null
        tab_layout.getTabAt(MARS)?.customView = null
        tab_layout.getTabAt(MOON)?.customView = null

        when (position) {
            EARTH -> {
                setEarthTab(layoutInflater)
            }
            MARS -> {
                setMarsTab(layoutInflater)
            }
            MOON -> {
                setMoonTab(layoutInflater)
            }
            else -> {
                setEarthTab(layoutInflater)
            }
        }
    }

    private fun setEarthTab(layoutInflater: LayoutInflater) {
        val earth =
            layoutInflater.inflate(R.layout.activity_pages_tab_earth, null)

        tab_layout.getTabAt(EARTH)?.customView = earth
        tab_layout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_pages_tab_mars, null)
        tab_layout.getTabAt(MOON)?.customView =
            layoutInflater.inflate(R.layout.activity_pages_tab_moon, null)
    }

    private fun setMarsTab(layoutInflater: LayoutInflater) {
        val mars =
            layoutInflater.inflate(R.layout.activity_pages_tab_mars, null)

        tab_layout.getTabAt(MARS)?.customView = mars
        tab_layout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_pages_tab_earth, null)
        tab_layout.getTabAt(MOON)?.customView =
            layoutInflater.inflate(R.layout.activity_pages_tab_moon, null)
    }

    private fun setMoonTab(layoutInflater: LayoutInflater) {
        val moon =
            layoutInflater.inflate(R.layout.activity_pages_tab_moon, null)

        tab_layout.getTabAt(MOON)?.customView = moon
        tab_layout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_pages_tab_earth, null)
        tab_layout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_pages_tab_mars, null)
    }
}