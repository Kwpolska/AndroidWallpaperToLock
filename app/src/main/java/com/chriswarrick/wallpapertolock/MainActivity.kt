package com.chriswarrick.wallpapertolock

import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chriswarrick.wallpapertolock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.content.button.setOnClickListener { performCopy() }
    }

    fun performCopy() {
        val manager = WallpaperManager.getInstance(applicationContext)
        Log.i("WallpaperToLock", "Setting wallpaper...")
        showMessage(R.string.status_wait)

        if (!Environment.isExternalStorageManager()) {
            showMessage(R.string.status_permission_required)
            Log.e("WallpaperToLock", "Permissions are missing.")
            Toast.makeText(this, R.string.permission_toast, Toast.LENGTH_LONG).show()
            val uri = Uri.parse("package:${applicationContext.packageName}")

            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    uri
                )
            )
            return
        }

        try {
            manager.getWallpaperFile(WallpaperManager.FLAG_SYSTEM).use { wallpaperFile ->
                ParcelFileDescriptor.AutoCloseInputStream(wallpaperFile).use { wallpaperStream ->
                    manager.setStream(wallpaperStream, null, true, WallpaperManager.FLAG_LOCK)
                }
            }
            Log.i("WallpaperToLock", "Wallpaper set successfully.")
            showMessage(R.string.status_ok)
        } catch (e: java.io.IOException) {
            Log.e("WallpaperToLock", "Failed to set wallpaper", e)
            showMessage(getString(R.string.status_failed, e))
        }
    }

    fun showMessage(id: Int) {
        showMessage(getString(id))
    }

    fun showMessage(text: String) {
        Log.i("WallpaperToLock", "Status: $text")
        binding.content.statusText.text = text
    }
}