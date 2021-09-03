package com.rembren.avonltd_assignment.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rembren.avonltd_assignment.classes.SongLoaderKt
import com.rembren.avonltd_assignment.models.SongDataKt
import java.io.File

/**
 * Is used to make [SongLoaderKt] loadData.
 * Notifies all subscribed classes on resulting
 */
class MainViewModel : ViewModel() {

  private var songData: MutableLiveData<Array<SongDataKt>>? = null

  fun initialize(defaultThumbnail: Bitmap, startDirectory: File) {
    if (songData == null) {
      songData = MutableLiveData()
      loadData(defaultThumbnail, startDirectory)
    }
  }

  fun getData(): LiveData<Array<SongDataKt>> = songData!!

  /**
   * Initiates the process of loading data about audio files.
   *
   * @param defaultThumbnail in case an audio file doesn't have a thumbnail stored in it's id3 tag
   *                         this will be used as it's thumbnail
   * @param startDirectory   a directory in the file system that will be the starting point for
   *                         depth-first search for audio files
   */
  private fun loadData(defaultThumbnail: Bitmap, startDirectory: File) {
    val songLoaderKt = SongLoaderKt(defaultThumbnail, startDirectory)

    songLoaderKt.loadData {
      songData?.postValue(it)
    }
  }

}