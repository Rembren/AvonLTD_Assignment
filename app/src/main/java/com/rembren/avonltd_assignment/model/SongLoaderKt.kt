package com.rembren.avonltd_assignment.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import com.rembren.avonltd_assignment.classes.SongDataKt

class SongLoaderKt(rootDirectory: String) {

    fun searchForSong(directory: String): Array<SongDataKt> {

    }

    fun getThumbnail(path: String): Bitmap? {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(path)

        val embeddedPicture = mediaMetadataRetriever.embeddedPicture
        mediaMetadataRetriever.release()

        return if (embeddedPicture != null) {
            BitmapFactory.decodeByteArray(
                embeddedPicture,
                0,
                embeddedPicture.size
            )
        } else {
            //change to default bitmap
            null
        }
    }
}