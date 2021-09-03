package com.rembren.avonltd_assignment.models

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import java.io.File

/**
 * Data class, contains most of the important information required to represent song on the
 * screen
 */
class SongDataKt(private val file: File, val thumbnail: Bitmap?, val gradientColor: IntArray) {

  var songName: String
  var authorName: String

  init {
    val stringArray = file.nameWithoutExtension.split(" - ")
    if (stringArray.size > 1) {
      songName = stringArray[1]
      authorName = stringArray[0]
    } else {
      songName = stringArray[0]
      authorName = "Unknown"
    }
  }

  /**
   * This gradient drawable will be used as a background for fragment which displays
   * this data object
   */
  val gradientDrawable: GradientDrawable = GradientDrawable().apply {
    gradientType = GradientDrawable.LINEAR_GRADIENT
    orientation = GradientDrawable.Orientation.TOP_BOTTOM
    shape = GradientDrawable.RECTANGLE
    colors = gradientColor
  }

  /**
   * debugging stuff
   */
  override fun toString(): String {
    return "file name: ${file.nameWithoutExtension}; color: $gradientColor thumbnail not null: ${thumbnail != null}"
  }

  companion object {
    const val GRADIENT_COLORS_NUMBER: Int = 3
    const val TOP_COLOR_INDEX = 0
    const val BOTTOM_COLOR_INDEX = 2
  }
}