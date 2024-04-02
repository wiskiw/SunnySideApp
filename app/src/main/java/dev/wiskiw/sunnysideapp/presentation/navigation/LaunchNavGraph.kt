package dev.wiskiw.sunnysideapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.wiskiw.sunnysideapp.presentation.screen.home.HomeScreen

object LaunchNavGraph {

    enum class Destination {
        Home,
    }

    fun NavGraphBuilder.graph() = navigation(
        startDestination = Destination.Home.name,
        route = Route.Launch.name,
    ) {
        composable(Destination.Home.name) { HomeScreen() }
    }
}
