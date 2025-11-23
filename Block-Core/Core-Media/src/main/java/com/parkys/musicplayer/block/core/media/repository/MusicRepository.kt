package com.parkys.musicplayer.block.core.media.repository

import com.parkys.musicplayer.block.core.media.model.Music


interface MusicRepository {

    /** 전체 음악 목록 */
    suspend fun getAllMusic(): List<Music>

    /** ID 로 특정 음악 가져오기 */
    suspend fun getMusicById(id: Long): Music?
}