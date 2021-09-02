package com.rembren.avonltd_assignment.views

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.rembren.avonltd_assignment.models.SongDataKt
import com.rembren.avonltd_assignment.databinding.FragmentSongDataBinding
import com.rembren.avonltd_assignment.viewmodels.MainViewModel


private const val SONG_DATA_INDEX = "song_data"

@BindingAdapter("bitmap")
fun setImageResource(imageView: ImageView, bitmap: Bitmap?) {
  if (bitmap != null)
    imageView.setImageBitmap(bitmap)
}

@BindingAdapter("gradientBackground")
fun setBackgroundDrawable(layout: ConstraintLayout, gradientColors: GradientDrawable) {
  layout.background = gradientColors
}


class SongDataFragment : Fragment() {

  private var songDataIndex: Int? = null
  var songDataKt: SongDataKt? = null
  private var _binding: FragmentSongDataBinding? = null

  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      songDataIndex = it.getInt(SONG_DATA_INDEX)
    }

    val mainViewModel: MainViewModel =
      ViewModelProviders.of(requireActivity())[MainViewModel::class.java]
    val dataArray = mainViewModel.getData().value

    if (dataArray == null) {
      throw IllegalStateException("data array is null $dataArray")
    }

    songDataKt = dataArray[songDataIndex!!]
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {

    _binding = FragmentSongDataBinding.inflate(inflater, container, false)
    _binding?.fragment = this
    return binding.root
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