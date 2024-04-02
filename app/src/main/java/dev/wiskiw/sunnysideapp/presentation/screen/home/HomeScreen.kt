package dev.wiskiw.sunnysideapp.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.wiskiw.sunnysideapp.ui.theme.SunnySideAppTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Hello world!",
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SunnySideAppTheme {
        HomeScreen()
    }
}
