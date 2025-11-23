package com.parkys.musicplayer.block.core.media.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @param id: MediaStore 음원의 고유 ID
 * @param title: 곡 제목
 * @param artist: 아티스트
 * @param album: 앨범명
 * @param duration: 곡의 길이
 * @param contentUri: ExoPlayer로 직접 재생 가능한 Uri
 * @param albumArtUri: 앨범 아트
 */

@Parcelize
data class Music(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val duration: Long,
    val contentUri: Uri,
    val albumArtUri: Uri
) : Parcelable