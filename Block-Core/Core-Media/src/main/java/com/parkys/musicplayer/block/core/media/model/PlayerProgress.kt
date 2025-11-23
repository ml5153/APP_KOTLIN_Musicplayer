package com.parkys.musicplayer.block.core.media.model

/**
 * @param duration: 음악 길이
 * @param currentPosition: 햔재 재생 위치
 * @param bufferedPosition: 현재 버퍼링 된 위치
 */


data class PlayerProgress (
    val duration: Long,
    val currentPosition: Long,
    val bufferedPosition: Long
)
