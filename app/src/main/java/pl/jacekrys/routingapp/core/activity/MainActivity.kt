package pl.jacekrys.routingapp.core.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import pl.jacekrys.routingapp.core.navigation.NavigationEvent
import pl.jacekrys.routingapp.core.navigation.Navigator
import pl.jacekrys.routingapp.core.navigation.Screen
import pl.jacekrys.routingapp.core.navigation.getLastBackStackDestinationRoute
import pl.jacekrys.routingapp.core.ui.theme.RoutingAppTheme
import pl.jacekrys.routingapp.feature.route.presentation.details.RouteDetailsScreen
import pl.jacekrys.routingapp.feature.route.presentation.details.RouteDetailsViewModel
import pl.jacekrys.routingapp.feature.route.presentation.list.RouteListScreen
import pl.jacekrys.routingapp.feature.route.presentation.list.RouteListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navigator: Navigator = koinInject()
            RoutingAppTheme {
                LaunchedEffect(key1 = Unit) {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.RESUMED) {
                            navigator.navigationEvents.collectLatest { navigationEvent ->
                                when (navigationEvent) {
                                    NavigationEvent.Back -> {
                                        if (navController.previousBackStackEntry == null) {
                                            this@MainActivity.finish()
                                        } else
                                            navController.navigateUp()
                                    }

                                    is NavigationEvent.Destination ->
                                        navController.navigate(
                                            route = navigationEvent.screen.route,
                                        )
                                }
                            }
                        }
                    }
                }

                BackHandler {
                    navigator.navigateBack()
                }

                NavHost(
                    navController = navController,
                    startDestination = Navigator.START_DESTINATION.route
                ) {
                    composable(Screen.RoutesList.route) {
                        val viewModel = koinViewModel<RouteListViewModel>()
                        RouteListScreen(viewModel = viewModel)
                    }
                    composable(Screen.RouteDetails.route) {
                        val viewModel = koinViewModel<RouteDetailsViewModel>()
                        RouteDetailsScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoutingAppTheme {
        Greeting("Android")
    }
}