package com.rembren.avonltd_assignment.models

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import java.io.File

class SongDataKt(val file: File, val thumbnail: Bitmap?, val gradientColor: IntArray) {

  companion object {
    const val COLOR_ARRAY_SIZE: Int = 3
  }

  val gradientDrawable: GradientDrawable = GradientDrawable().apply {
    gradientType = GradientDrawable.LINEAR_GRADIENT
    orientation = GradientDrawable.Orientation.TOP_BOTTOM
    shape = GradientDrawable.RECTANGLE
    //orientation = GradientDrawable.Orientation.TL_BR
    setGradientCenter(0.5F, 0.4F)
    colors = gradientColor
    gradientRadius = 1200F

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      innerRadiusRatio = 0.4F
      thicknessRatio = 0.3F
    }
  }

  override fun toString(): String {
    return "file name: ${file.name}; color: $gradientColor thumbnail not null: ${thumbnail != null}"
  }

}