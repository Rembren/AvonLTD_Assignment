package com.rembren.avonltd_assignment.classes;

import android.graphics.Bitmap;
import android.graphics.Color;

public class SongData {

  private Bitmap thumbnail;
  private String filePath;
  private Color[] gradientColors;

  public SongData(Bitmap thumbnail, String filePath, Color[] gradientColors) {
    this.thumbnail = thumbnail;
    this.filePath = filePath;
    this.gradientColors = gradientColors;
  }

  public Bitmap getThumbnail() {
    return thumbnail;
  }

  public String getFilePath() {
    return filePath;
  }

  public Color[] getGradientColors() {
    return gradientColors;
  }

}
