package com.rembren.avonltd_assignment.classes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaMetadataRetriever
import com.rembren.avonltd_assignment.models.SongDataKt
import com.rembren.avonltd_assignment.models.SongDataKt.Companion.GRADIENT_COLORS_NUMBER
import java.io.File
import java.util.*
import java.util.Collections.shuffle
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

/**
 * Deep-searches for all audio files in specified directory and
 * gathers important data into SongDataKt objects
 *
 * This is a long running operation and should be performed in background thread
 */
class SongLoaderKt(private val defaultThumbnail: Bitmap, private val startDirectory: File) {

  /** For convenience, return it if search was unsuccessful */
  private val emptySongDataArray = ArrayList<SongDataKt>()

  /**
   * Since in future this process can take up a lot of time it is better to put in into
   * separate thread
   */
  fun loadData(callback: (Array<SongDataKt>) -> Unit) {
    val executor = Executors.newSingleThreadExecutor()
    val worker = Runnable {
      val resultArrayList = searchForSongsRecursively(startDirectory)
      // one of ways to display a different song from the list each time
      shuffle(resultArrayList)
      callback.invoke(resultArrayList.toTypedArray())
    }
    executor.execute(worker)
    executor.shutdown()
  }

  /**
   * Recursive method that deep-searches for audio files starting from given directory
   *
   * @param directory - a folder in the file system
   */
  private fun searchForSongsRecursively(directory: File): ArrayList<SongDataKt> {
    val contentList = directory.listFiles()
    val listOfSongData = ArrayList<SongDataKt>()

    if (contentList == null || contentList.isEmpty()) {
      return emptySongDataArray
    }

    for (file in contentList) {
      // deep search happens here
      if (file.isDirectory) {
        listOfSongData.addAll(searchForSongsRecursively(file))
      }

      if (isAudioFile(file.name)) {
        val thumbnail = getThumbnail(file)

        listOfSongData.add(SongDataKt(
          file,
          thumbnail,
          getGradientColors(thumbnail)))
      }
    }
    return listOfSongData
  }

  /**
   * Identifies whether the file is audio file by comparing it's extension with ones stored in
   * [SupportedExtensions]
   */
  private fun isAudioFile(fileName: String): Boolean {
    val extension: String = getFileExtension(fileName) ?: return false
    // valueOf doesn't have negative result, it throws an exception instead, so we catch it
    try {
      SupportedExtensions.valueOf(extension.uppercase(Locale.getDefault()))
    } catch (e: IllegalArgumentException) {
      return false
    }
    return true
  }

  /**
   * @return string with file extension (dot not included) if there is one
   *         null, if file extension was not found
   */
  private fun getFileExtension(fileName: String): String? {
    val lastDotIndex = fileName.lastIndexOf('.')
    return if (lastDotIndex > 0) {
      fileName.substring(lastDotIndex + 1)
    } else
      null
  }

  /**
   * If there is a thumbnail stored in id3 tag - retrieves it and
   * converts to Bitmap. If image was not found - uses default bitmap that was
   * passed into constructor
   */
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

  /**
   * Retrieves three colors that are average for top, middle and bottom parts of the image
   * that will be later used to create gradient
   *
   * Makes it feel a little bit more natural
   */
  private fun getGradientColors(bitmap: Bitmap): IntArray {
    val result = ArrayList<Int>(GRADIENT_COLORS_NUMBER)
    val sampleStripesHeight = arrayOf(1, bitmap.height / 2, bitmap.height - 1)

    for (iteration in 0 until GRADIENT_COLORS_NUMBER) {
      var redBucket = 0
      var greenBucket = 0
      var blueBucket = 0

      val coordinateY = sampleStripesHeight[iteration]
      for (coordinateX in 0 until bitmap.width) {
        val currentPixelColor = bitmap.getPixel(coordinateX, coordinateY)
        redBucket += Color.red(currentPixelColor)
        greenBucket += Color.green(currentPixelColor)
        blueBucket += Color.blue(currentPixelColor)
      }

      result.add(Color.rgb(
        redBucket / bitmap.width,
        greenBucket / bitmap.width,
        blueBucket / bitmap.width))
    }

    return result.toIntArray()
  }

  /**
   * Enumerates audio extensions supported by android
   */
  enum class SupportedExtensions {

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