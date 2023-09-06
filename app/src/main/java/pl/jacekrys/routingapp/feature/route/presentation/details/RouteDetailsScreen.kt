package pl.jacekrys.routingapp.feature.route.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(
    viewModel: RouteDetailsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10f)
    }
    val scaffoldState = rememberBottomSheetScaffoldState()

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    viewModel.getRouteDetails()
                }

                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = state) {
        state.route?.getCameraBounds()?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(
                    it,
                    100
                ),
                durationMs = 1000
            )
        }
    }

    RouteDetailsScreenContent(
        state = state,
        scaffoldState = scaffoldState,
        cameraPositionState = cameraPositionState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreenContent(
    state: RouteDetailsState,
    scaffoldState: BottomSheetScaffoldState,
    cameraPositionState: CameraPositionState
) {


    BottomSheetScaffold(
        sheetContent = {
            RouteDetailsBottomSheetContent(state)
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 96.dp,
        sheetContainerColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetShadowElevation = 8.dp,
        sheetDragHandle = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(100f))
                        .background(Color.Black)
                        .height(8.dp)
                        .width(60.dp)
                )
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        "Route details", style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        )
                    )
                }
            }
        }
    ) {
        Box {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                state.route?.stops?.forEachIndexed { idx, stop ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                stop.coordinates.latitude.toDouble(),
                                stop.coordinates.longitude.toDouble()
                            )
                        ),
                        title = "Stop ${idx + 1}",
                    )
                }
            }
            MapAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                title = "Route name",
                onBackClicked = {}
            )
        }
    }
}

@Composable
fun MapAppBar(
    modifier: Modifier,
    title: String,
    onBackClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .shadow(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            title, style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
            )
        )
    }
}

@Composable
fun RouteDetailsBottomSheetContent(state: RouteDetailsState) {

}

@Preview
@Composable
fun MapAppBarPreview() {
    MapAppBar(Modifier, "Route name", {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun RouteDetailsScreenPreview() {
    RouteDetailsScreenContent(
        state = RouteDetailsState(),
        scaffoldState = rememberBottomSheetScaffoldState(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10f)
        }
    )
}