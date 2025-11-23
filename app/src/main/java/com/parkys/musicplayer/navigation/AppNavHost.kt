package com.parkys.musicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.parkys.musicplayer.block.feature.list.ui.MusicListScreen
import com.parkys.musicplayer.block.feature.player.ui.PlayerScreen
import com.parkys.musicplayer.block.feature.list.presentation.MusicListViewModel
import com.parkys.musicplayer.block.feature.player.presentation.PlayerViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    listViewModel: MusicListViewModel,
    playerViewModel: PlayerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Route.List.route
    ) {

        composable(Route.List.route) {
            MusicListScreen(
                viewModel = listViewModel,
                onMusicClick = { music ->
                    val list = listViewModel.musicList.value
                    playerViewModel.setPlaylist(list)

                    navController.navigate(Route.Player.passId(music.id))
                }
            )
        }

        composable(
            route = Route.Player.route,
            arguments = listOf(
                navArgument("musicId") { type = NavType.LongType }
            )
        ) { entry ->

            val id = entry.arguments?.getLong("musicId") ?: -1

            PlayerScreen(
                musicId = id,
                viewModel = playerViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
