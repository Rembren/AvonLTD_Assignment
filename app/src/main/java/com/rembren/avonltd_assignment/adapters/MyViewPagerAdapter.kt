package com.rembren.avonltd_assignment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rembren.avonltd_assignment.models.SongDataKt
import com.rembren.avonltd_assignment.views.SongDataFragment

/**
 * Adapter that will be used with ViewPager to display fragments
 *
 * Somewhat of a hack was used to implement looped two way scrolling.
 * Taken from here: https://stackoverflow.com/a/16670652
 */
class MyViewPagerAdapter(fragmentActivity: FragmentActivity,
                         private val songData: Array<SongDataKt>) :
  FragmentStateAdapter(fragmentActivity) {

  override fun getItemCount(): Int = Int.MAX_VALUE

  override fun createFragment(position: Int): Fragment =
    SongDataFragment.newInstance(position % songData.size)

}