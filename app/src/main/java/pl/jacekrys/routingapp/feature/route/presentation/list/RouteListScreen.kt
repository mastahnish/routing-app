package pl.jacekrys.routingapp.feature.route.presentation.list


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.jacekrys.routingapp.R
import pl.jacekrys.routingapp.core.ui.components.CustomTextField
import pl.jacekrys.routingapp.core.ui.theme.NeonGreen
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.presentation.list.component.ErrorView
import pl.jacekrys.routingapp.feature.route.presentation.list.component.Logo
import pl.jacekrys.routingapp.feature.route.presentation.list.component.RouteItem

@Composable
fun RouteListScreen(
    viewModel: RouteListViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    viewModel.getRoutes()
                }

                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    RouteListScreenContent(state = state,
        onSearchTextChange = { viewModel.updateSearchText(it) },
        onRetryClick = { viewModel.getRoutes() },
        onRouteClicked = { viewModel.chooseRoute(it) }
    )
}

@Composable
fun RouteListScreenContent(
    state: RouteListState,
    onRetryClick: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onRouteClicked: (Route) -> Unit
) {
    Column {
        LogoWithSearchBar(
            searchText = state.searchText,
            onSearchTextChange = onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        when {
            state.isListLoading -> ListLoadingView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            state.errorInfo != null -> ErrorView(
                modifier = Modifier.weight(1f),
                errorMessage = state.errorInfo,
                onRetryClick = onRetryClick
            )

            state.routes.isEmpty() -> NoResultsView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )

            else -> RoutesList(
                routes = state.routes,
                onRouteClicked = onRouteClicked,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun LogoWithSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Logo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            stringResource(id = R.string.greeting),
            style = TextStyle(
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            stringResource(id = R.string.greeting_subtext),
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
        )
        CustomTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            placeholder = stringResource(id = R.string.route_search_placeholder),
            modifier = Modifier
                .padding(top = 4.dp, bottom = 24.dp)
        )
    }
}

@Composable
fun NoResultsView(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.no_results_info),
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ListLoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = NeonGreen
        )
    }
}

@Composable
fun RoutesList(
    routes: List<Route>,
    onRouteClicked: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Spacer(Modifier.height(20.dp))
        }
        itemsIndexed(routes) { index, item ->
            RouteItem(
                modifier = Modifier.fillMaxWidth(),
                route = item,
                onRouteClick = onRouteClicked
            )
            if (index < routes.lastIndex)
                Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RouteListScreenPreview() {
    RouteListScreenContent(
        state = RouteListState(
            routes = listOf(
                Route("", "Example 1", listOf()),
                Route("", "Example 2", listOf())
            ),
        ),
        onSearchTextChange = {},
        onRetryClick = {},
        onRouteClicked = {}
    )
}