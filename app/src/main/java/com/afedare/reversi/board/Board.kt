package com.afedare.reversi.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Board(cells: Array<Array<Color>>, onClick: () -> Unit) {
    val boardSize = cells.size
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3F8B50))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until boardSize) {
            Row(modifier = Modifier.border(1.dp, Color.Black)) {
                for (j in 0 until boardSize) {
                    Column(modifier = Modifier
                        .weight(1f, false)
                        .border(1.dp, Color.Black)) {
                        Cell(
                            color = cells[i][j],
                            onClick = onClick
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun Cell(color: Color = Color.Black, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .drawWithCache {
                onDrawBehind {
                    drawCircle(color)
                }
            }
            .size(50.dp)
            .padding(10.dp)
            .fillMaxWidth()
    )

}
