package com.rembren.avonltd_assignment.views

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.rembren.avonltd_assignment.databinding.FragmentSongDataBinding
import com.rembren.avonltd_assignment.models.SongDataKt
import com.rembren.avonltd_assignment.models.SongDataKt.Companion.BOTTOM_COLOR_INDEX
import com.rembren.avonltd_assignment.models.SongDataKt.Companion.TOP_COLOR_INDEX
import com.rembren.avonltd_assignment.viewmodels.MainViewModel


private const val SONG_DATA_INDEX = "song_data"
private const val COLOR_CHANGE_DURATION_MILLIS = 250L

@BindingAdapter("bitmap")
fun setImageResource(imageView: ImageView, bitmap: Bitmap?) {
  if (bitmap != null)
    imageView.setImageBitmap(bitmap)
}

@BindingAdapter("gradientBackground")
fun setBackgroundDrawable(layout: ConstraintLayout, gradientColors: GradientDrawable) {
  layout.background = gradientColors
}

/**
 * Displays data stored in [SongDataKt], used in PageView
 */
class SongDataFragment : Fragment() {

  /** tells the fragment which element of [songDataKt] array should be displayed */
  private var songDataIndex: Int? = null
  var songDataKt: SongDataKt? = null

  // convenient way to store binding element so it can be destroyed with fragment
  private var _binding: FragmentSongDataBinding? = null
  private val binding get() = _binding!!


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      songDataIndex = it.getInt(SONG_DATA_INDEX)
    }
    // a way to share viewModel with MainActivity
    val mainViewModel: MainViewModel =
      ViewModelProviders.of(requireActivity())[MainViewModel::class.java]
    // get an array of data that is to be displayed from the viewModel
    val dataArray =
      mainViewModel.getData().value ?: throw IllegalStateException("data array is null")

    songDataKt = dataArray[songDataIndex!!]
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

    _binding = FragmentSongDataBinding.inflate(inflater, container, false)
    _binding?.fragment = this
    return binding.root
  }

  /**
   * Whenever the fragment is ready to interact with the user - we should animate the transition
   * of status bar and navigation bar colors, so it is a bit more appealing
   */
  override fun onResume() {
    super.onResume()
    transitionWindowColors(
      activity?.window?.statusBarColor!!,
      songDataKt?.gradientColor!![TOP_COLOR_INDEX], 0)
    transitionWindowColors(
      activity?.window?.navigationBarColor!!,
      songDataKt?.gradientColor!![BOTTOM_COLOR_INDEX], 1)
  }

  /**
   * Starts animation that will smoothly transition background colors of statusBar or navigationBar
   *
   * @param fieldId 0 to change color of statusBar
   *                1 to change color of navigationBar
   *                all other values will simply not pass
   */
  private fun transitionWindowColors(colorFrom: Int, colorTo: Int, fieldId: Int) {
    if (fieldId > 1 || fieldId < 0)
      return

    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = COLOR_CHANGE_DURATION_MILLIS

    colorAnimation.addUpdateListener { animator ->
      when (fieldId) {
        0 -> activity?.window?.statusBarColor = (animator.animatedValue as Int)
        1 -> activity?.window?.navigationBarColor = (animator.animatedValue as Int)
      }
    }
    colorAnimation.start()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    @JvmStatic
    fun newInstance(songDataIndex: Int) =
      SongDataFragment().apply {
        arguments = Bundle().apply {
          putInt(SONG_DATA_INDEX, songDataIndex)
        }
      }
  }
}