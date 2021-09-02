package com.rembren.avonltd_assignment.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rembren.avonltd_assignment.classes.SongDataKt
import com.rembren.avonltd_assignment.models.SongLoaderKt
import java.io.File

class MainViewModel : ViewModel() {

  private var songData: MutableLiveData<Array<SongDataKt>>? = null

  fun init(defaultThumbnail: Bitmap,
           startDirectory: File?) {
    if (startDirectory == null)
      throw IllegalArgumentException("Directory should not be null")

    if (songData == null) {
      songData = MutableLiveData()
      loadData(defaultThumbnail, startDirectory)
    }
  }

  fun getData(): LiveData<Array<SongDataKt>> {
    return songData!!
  }


  private fun loadData(defaultThumbnail: Bitmap, startDirectory: File) {
    val songLoaderKt = SongLoaderKt(defaultThumbnail, startDirectory)
    songLoaderKt.loadData {
      songData?.postValue(it)
    }
  }

}