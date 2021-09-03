package com.rembren.avonltd_assignment.views

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.rembren.avonltd_assignment.R
import com.rembren.avonltd_assignment.adapters.MyViewPagerAdapter
import com.rembren.avonltd_assignment.databinding.ActivityMainBinding
import com.rembren.avonltd_assignment.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {

  companion object {
    private const val PERMISSION_REQUEST_CODE: Int = 2143
  }

  private val mainViewModel: MainViewModel by viewModels()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mainBinding =
      DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    if (askForPermissions())
      loadData()
  }

  private fun loadData() {
    // TODO: change to another method
    val startDirectory =
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    mainViewModel.init(
      BitmapFactory.decodeResource(resources, R.drawable.default_thumbnail),
      startDirectory)

    val data = mainViewModel.getData()

    data.observe(this) { newArray ->
      Log.d("TAG1", "data observed")
      val viewPager: ViewPager2 = findViewById(R.id.temporary)
      viewPager.adapter = MyViewPagerAdapter(
        this,
        newArray)
    }
  }

  private fun askForPermissions(): Boolean {
    return if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
      PackageManager.PERMISSION_GRANTED) {
      true
    } else {
      requestPermissions(arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE),
        PERMISSION_REQUEST_CODE)
      false
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int,
                                          permissions: Array<String>, grantResults: IntArray) {

    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      PERMISSION_REQUEST_CODE -> {
        // If request is cancelled, the result arrays are empty.
        if ((grantResults.isNotEmpty() &&
              grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
          loadData()
        } else {
          // TODO: add message
        }
        return
      }
      else -> {
        // Ignore all other requests.
      }
    }
  }
}