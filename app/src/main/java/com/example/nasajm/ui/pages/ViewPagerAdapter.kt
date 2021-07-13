package com.example.nasajm.ui.pages

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasajm.ui.pages.earth.EarthFragment
import com.example.nasajm.ui.pages.mars.MarsFragment


class ViewPagerAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), MoonFragment())

    fun getFragmentName(position: Int): String {
        return fragments[position].javaClass.simpleName
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}