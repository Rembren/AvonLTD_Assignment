package com.rembren.avonltd_assignment.adapters

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class SongDataBindingAdapter {

  @BindingAdapter("bitmap")
  fun setImageResource(imageView: ImageView, bitmap: Bitmap) {
    imageView.setImageBitmap(bitmap)
  }
}