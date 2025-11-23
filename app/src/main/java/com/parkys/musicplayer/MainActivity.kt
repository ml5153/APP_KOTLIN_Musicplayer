package com.parkys.musicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.parkys.musicplayer.block.core.media.repository.MusicRepositoryImpl
import com.parkys.musicplayer.block.feature.list.presentation.MusicListViewModel
import com.parkys.musicplayer.block.feature.list.presentation.MusicListViewModelFactory
import com.parkys.musicplayer.block.feature.player.presentation.PlayerViewModel
import com.parkys.musicplayer.block.feature.player.presentation.PlayerViewModelFactory
import com.parkys.musicplayer.navigation.AppNavHost


internal class MainActivity : ComponentActivity() {

    private val listViewModel: MusicListViewModel by viewModels {
        MusicListViewModelFactory(
            MusicRepositoryImpl(applicationContext)
        )
    }

    private val playerViewModel: PlayerViewModel by viewModels {
        PlayerViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAllPermissions()

        setContent {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
                listViewModel = listViewModel,
                playerViewModel = playerViewModel
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (requestCode == 100) {
            val allGranted = grantResults.isNotEmpty() &&
                    grantResults.all { it == PackageManager.PERMISSION_GRANTED }

            if (allGranted) {
                listViewModel.loadMusic()
            } else {
                Toast.makeText(
                    this,
                    "알림, 오디오 권한을 모두 허용 해야 앱을 사용할 수 있습니다.",
                    Toast.LENGTH_SHORT
                ).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    finishAffinity()
                }, 500)
            }
        }
    }

    private fun checkAllPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO)
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                100
            )
        } else {
            listViewModel.loadMusic()
        }
    }
}