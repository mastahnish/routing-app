package pl.jacekrys.routingapp.feature.route.presentation.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.jacekrys.routingapp.R

@Composable
fun ErrorView(
    modifier: Modifier,
    errorMessage: String,
    onRetryClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorInfo(
            modifier = Modifier
                .weight(1f),
            errorMessage = errorMessage
        )
        Button(
            onClick = { onRetryClick() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD3F268),
                contentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.retry), style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 23.5.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}


@Composable
fun ErrorInfo(modifier: Modifier, errorMessage: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_no_connection),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.error_title), style = TextStyle(
                fontSize = 32.sp,
                lineHeight = 23.5.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = errorMessage, style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 23.5.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Preview
@Composable
fun NoConnectionPreview() {
    ErrorView(modifier = Modifier, errorMessage = "No connection")
}