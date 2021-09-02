package com.rembren.avonltd_assignment.models

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import java.io.File

class SongDataKt(val file: File, val thumbnail: Bitmap?, private val gradientColor: IntArray) {

  companion object {
    const val COLOR_ARRAY_SIZE: Int = 3
  }

  val gradientDrawable: GradientDrawable = GradientDrawable().apply {
    orientation = GradientDrawable.Orientation.TL_BR
    setGradientCenter(0.5F, 0.3F)
    colors = gradientColor
    gradientType = GradientDrawable.RADIAL_GRADIENT


  }

  override fun toString(): String {
    return "file name: ${file.name}; color: $gradientColor thumbnail not null: ${thumbnail != null}"
  }

}