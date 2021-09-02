package com.rembren.avonltd_assignment.classes

import android.graphics.Bitmap
import android.graphics.Color
import java.io.File

public class SongDataKt(val file: File, val thumbnail: Bitmap?, val gradientColor: Array<Color>) {

  companion object {
    const val COLOR_ARRAY_SIZE: Int = 3
  }

  override fun toString(): String {
    return "file name: ${file.name}; color: $gradientColor"
  }

}