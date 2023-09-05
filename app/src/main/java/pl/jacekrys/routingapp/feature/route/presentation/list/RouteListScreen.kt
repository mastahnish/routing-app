package pl.jacekrys.routingapp.feature.route.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.jacekrys.routingapp.R
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.presentation.list.component.RouteItem

@Composable
fun RouteListScreen(
    viewModel: RouteListViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RouteListScreenContent(state = state)
}

@Composable
fun RouteListScreenContent(state: RouteListState) {
    Column {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
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
                "Good morning!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Where we go today?",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
            )
            CustomTextField(
                value = "Birmingham",
                onValueChange = {},
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 24.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(Modifier.height(20.dp))
            }
            itemsIndexed(state.routes) { index, item ->
                RouteItem(modifier = Modifier.fillMaxWidth())
                if (index < state.routes.lastIndex)
                    Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun Logo(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            "Routing App", style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
            )
        )
    }
}

@Preview(backgroundColor = 0xFF000000)
@Composable
fun LogoPreview() {
    Logo(modifier = Modifier)
}

@Composable
fun CustomTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 35.dp)
            .clip(RoundedCornerShape(100))
            .background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Default.Search, contentDescription = null,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun RouteListScreenPreview() {
    RouteListScreenContent(
        state = RouteListState(
            routes = listOf(
                Route("", "", listOf()),
                Route("", "", listOf())
            )
        )
    )
}