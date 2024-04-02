package dev.wiskiw.sunnysideapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.wiskiw.sunnysideapp.presentation.navigation.LaunchNavGraph
import dev.wiskiw.sunnysideapp.presentation.navigation.Route
import dev.wiskiw.sunnysideapp.ui.theme.SunnySideAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SunnySideAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.Launch.name,
                ) {
                    with(LaunchNavGraph) { graph() }
                }
            }
        }
    }
}
