package com.rembren.avonltd_assignment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rembren.avonltd_assignment.models.SongDataKt
import com.rembren.avonltd_assignment.views.SongDataFragment

class MyViewPagerAdapter(fragmentActivity: FragmentActivity,
                                private val songData: Array<SongDataKt>) :
  FragmentStateAdapter(fragmentActivity) {

  override fun getItemCount(): Int = Int.MAX_VALUE

  override fun createFragment(position: Int): Fragment =
    SongDataFragment.newInstance(position % songData.size)

}