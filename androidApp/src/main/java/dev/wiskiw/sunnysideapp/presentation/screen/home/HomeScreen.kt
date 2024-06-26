package dev.wiskiw.sunnysideapp.presentation.screen.home

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dev.wiskiw.sunnysideapp.presentation.theme.SunnySideAppTheme
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val locationPermissionsState = rememberMultiplePermissionsState(listOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    LaunchedEffect(locationPermissionsState) {
        if (locationPermissionsState.allPermissionsGranted) {
            viewModel.handleEvent(HomeViewModel.Event.LocationPermissionGranted)
        }
    }

    Content(
        modifier = modifier,
        locationPermissionsState = locationPermissionsState,
        address = viewModel.address,
        valueSourceCount = viewModel.valueSourceCount,
        unavailableSourceCount = viewModel.unavailableSourceCount,
        temperatureValue = viewModel.temperatureValue,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    locationPermissionsState: MultiplePermissionsState,
    address: String?,
    valueSourceCount: Int,
    unavailableSourceCount: Int,
    temperatureValue: String,
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (address != null) {
                    Text(
                        text = "Location: $address",
                        fontSize = 16.sp,
                    )
                }
                if (valueSourceCount > 0) {
                    Text(
                        text = "Available sources: $valueSourceCount",
                        fontSize = 16.sp,
                    )
                }
                if (unavailableSourceCount > 0) {
                    Text(
                        text = "Unavailable sources: $unavailableSourceCount",
                        fontSize = 16.sp,
                    )
                }
                if (temperatureValue.isNotBlank()) {
                    Text(
                        text = temperatureValue,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                EnsureLocationPermission(
                    locationPermissionsState = locationPermissionsState,
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun EnsureLocationPermission(
    locationPermissionsState: MultiplePermissionsState,
) {
    if (!locationPermissionsState.allPermissionsGranted) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size == locationPermissionsState.revokedPermissions.size

            val textToShow = if (locationPermissionsState.shouldShowRationale) {
                // Both location permissions have been denied
                "Getting your exact location is important for this app. " +
                        "Please grant us fine location. Thank you :D"
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                "This feature requires location permission"
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow precise location"
            } else {
                "Provide permissions"
            }

            Text(text = textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { locationPermissionsState.launchMultiplePermissionRequest() }
            ) {
                Text(buttonText)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    val emptyPermissionsState = object : MultiplePermissionsState {
        override val allPermissionsGranted: Boolean
            get() = true
        override val permissions: List<PermissionState>
            get() = emptyList()
        override val revokedPermissions: List<PermissionState>
            get() = emptyList()
        override val shouldShowRationale: Boolean
            get() = false

        override fun launchMultiplePermissionRequest() {}
    }

    SunnySideAppTheme {
        Content(
            modifier = Modifier,
            locationPermissionsState = emptyPermissionsState,
            address = "Preview address",
            valueSourceCount = 3,
            unavailableSourceCount = 1,
            temperatureValue = "23°C",
        )
    }
}
