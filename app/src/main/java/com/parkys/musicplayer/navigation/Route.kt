package com.parkys.musicplayer.navigation

sealed class Route(val route: String) {
    object List : Route("list")

    object Player : Route("player/{musicId}") {
        fun passId(id: Long) = "player/$id"
    }
}