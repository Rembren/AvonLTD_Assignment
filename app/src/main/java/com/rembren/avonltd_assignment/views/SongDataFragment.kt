package com.rembren.avonltd_assignment.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rembren.avonltd_assignment.classes.SongDataKt
import com.rembren.avonltd_assignment.databinding.FragmentSongDataBinding
import com.rembren.avonltd_assignment.viewmodels.MainViewModel


private const val SONG_DATA_INDEX = "song_data"

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
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    Log.d("TAG1", "on createview")
    val mainViewModel: MainViewModel by viewModels()
    val dataArray = mainViewModel.getData().value
    if (dataArray == null) {
      throw IllegalStateException("data array is null $dataArray")
    }
    songDataKt = dataArray[songDataIndex!!]
    _binding = FragmentSongDataBinding.inflate(inflater, container, false)
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