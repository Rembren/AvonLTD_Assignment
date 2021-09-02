package com.rembren.avonltd_assignment.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaMetadataRetriever
import com.rembren.avonltd_assignment.classes.SongDataKt
import com.rembren.avonltd_assignment.classes.SongDataKt.Companion.COLOR_ARRAY_SIZE
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

/**
 * Deep-searches for all audio files in specified directory and
 * gathers important data into SongDataKt objects
 *
 * This is a long running operation and should be performed in background thread
 */
class SongLoaderKt(private val defaultThumbnail: Bitmap, private val startDirectory: File) {

  private val emptySongDataArray: Array<SongDataKt> = emptyArray()

  fun loadData(callback: (Array<SongDataKt>) -> Unit) {
    val executor = Executors.newSingleThreadExecutor()
    val worker = Runnable {
      val result = searchForSongs(startDirectory)
      callback.invoke(result)
    }
    executor.execute(worker)
    executor.shutdown()
  }

  private fun searchForSongs(directory: File): Array<SongDataKt> {
    val contentList = directory.listFiles()
    val listOfSongs: ArrayList<SongDataKt> = ArrayList()

    if (contentList == null || contentList.isEmpty()) {
      return emptySongDataArray
    }

    for (file in contentList) {

      if (file.isDirectory) {
        listOfSongs.addAll(searchForSongs(file))
      }

      if (correctFileExtension(file.name)) {
        val thumbnail = getThumbnail(file)
        listOfSongs.add(SongDataKt(
          file,
          thumbnail,
          getRandomColors(thumbnail)))
      }
    }
    return listOfSongs.toTypedArray()
  }

  private fun correctFileExtension(fileName: String): Boolean {
    val extension: String = getFileExtension(fileName) ?: return false
    // valueOf doesn't have negative result, it throws an exception instead, so we catch it
    try {
      SupportedFormats.valueOf(extension.uppercase(Locale.getDefault()))
    } catch (e: IllegalArgumentException) {
      return false
    }
    return true
  }

  private fun getFileExtension(fileName: String): String? {
    val i = fileName.lastIndexOf('.')
    return if (i > 0) {
      fileName.substring(i + 1)
    } else null
  }

  private fun getThumbnail(file: File): Bitmap {
    val mediaMetadataRetriever = MediaMetadataRetriever()
    mediaMetadataRetriever.setDataSource(file.absolutePath)

    val embeddedPicture = mediaMetadataRetriever.embeddedPicture
    mediaMetadataRetriever.release()

    return if (embeddedPicture != null) {
      BitmapFactory.decodeByteArray(
        embeddedPicture,
        0,
        embeddedPicture.size
      )
    } else {
      defaultThumbnail
    }
  }

  private fun getRandomColors(bitmap: Bitmap): Array<Color> {
    val result = ArrayList<Color>(COLOR_ARRAY_SIZE)
    val random = Random()

    for (iteration in 0 until COLOR_ARRAY_SIZE) {
      val randomX = random.nextInt(bitmap.width)
      val randomY = random.nextInt(bitmap.height)
      val pixel = bitmap.getPixel(randomX, randomY)

      result.add(Color.valueOf(pixel))
    }
    return result.toTypedArray()
  }

  /**
   * Enumerates audio extensions supported by android
   */
  enum class SupportedFormats {

    MP4,
    M4A,
    AAC,
    TS,
    FLAC,
    MP3,
    MID,
    XMF,
    MXMF,
    RTTTL,
    RTX,
    OTA,
    IMY,
    OGG,
    MKV,
    WAV
  }
}