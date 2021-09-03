package com.rembren.avonltd_assignment.views

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rembren.avonltd_assignment.R
import com.rembren.avonltd_assignment.adapters.MyViewPagerAdapter
import com.rembren.avonltd_assignment.databinding.ActivityMainBinding
import com.rembren.avonltd_assignment.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {

  companion object {
    private const val STORAGE_PERMISSION_REQUEST_CODE: Int = 2143
  }

  private val mainViewModel: MainViewModel by lazy {
    ViewModelProviders.of(this)[MainViewModel::class.java]
  }

  private val mainBinding: ActivityMainBinding by lazy {
    DataBindingUtil.setContentView(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mainBinding.mainActivity = this

    if (askForPermissions())
      loadData()
  }

  /**
   * Initiate the process of loading data about audio
   */
  private fun loadData() {
    // for now this is hardcoded and deprecated, which is bad, but it is possible to add ability to
    // select a starting directory or to search everything
    val startDirectory =
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    mainViewModel.initialize(
      BitmapFactory.decodeResource(resources, R.drawable.default_thumbnail),
      startDirectory)

    val data = mainViewModel.getData()
    data.observe(this) { newArray ->
      mainBinding.viewPager.adapter = MyViewPagerAdapter(this, newArray)
      // used to implement two-way scrolling
      // could add a little random here to display different song each time and maintain list order
      mainBinding.viewPager.currentItem = Integer.MAX_VALUE / 2
    }
  }

  /**
   * Before starting search we should check if there is a permission to use external storage,
   * because if not - our search will always return nothing
   *
   * @return true - if there is a permission and search can be started
   *         false - if there is no permission, indicates that we should wait for user to grant
   *         permission before starting search
   */
  private fun askForPermissions(): Boolean {
    return if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
      PackageManager.PERMISSION_GRANTED) {
      true
    } else {
      requestPermissions(arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE),
        STORAGE_PERMISSION_REQUEST_CODE)
      false
    }
  }

  /**
   * Overly simplified, for now only works if user grants permission
   */
  override fun onRequestPermissionsResult(requestCode: Int,
                                          permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    when (requestCode) {
      STORAGE_PERMISSION_REQUEST_CODE -> {
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