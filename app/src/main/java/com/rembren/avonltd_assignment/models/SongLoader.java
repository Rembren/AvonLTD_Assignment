package com.rembren.avonltd_assignment.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import com.rembren.avonltd_assignment.classes.SongData;

/**
 * Will search through files to find list of available songs
 */
public class SongLoader {

  private String rootDirectory;

  public SongLoader(String rootDirectory) {
    this.rootDirectory = rootDirectory;
  }

  public SongData[] searchForSongs(String directory) {

    return null;
  }

  private Bitmap getThumbnail(String path) {
    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
    mediaMetadataRetriever.setDataSource(path);

    byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
    mediaMetadataRetriever.release();

    if (embeddedPicture != null) {
      return BitmapFactory.decodeByteArray(
          embeddedPicture,
          0,
          embeddedPicture.length);
    } else {
      return null;
    }
  }

}
