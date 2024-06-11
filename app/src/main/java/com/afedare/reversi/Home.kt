package com.afedare.reversi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun Home(nav: NavController? = null) {
    val singlePlayer = stringResource(R.string.single_player)
    val multiPlayerMinMax = stringResource(R.string.multi_minmax)
    val items: List<String> = mutableListOf(
        singlePlayer, multiPlayerMinMax
    )
    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items.forEach { message: String ->
            Text(message,
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(true, onClick = {
                        if (message == singlePlayer) {
                            nav?.navigate("single")
                        } else if (message == multiPlayerMinMax) {
                            nav?.navigate("multiminmax")
                        }
                    }), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}

