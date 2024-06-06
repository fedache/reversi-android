package com.afedare.reversi.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoardUi(
    cells: List<List<Color>>,
    availablePlays: List<Pair<Int, Int>>,
    currentCell: Color,
    onClick: (Int, Int) -> Unit
) {
    val boardSize = cells.size
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until boardSize) {
            Row(modifier = Modifier) {
                for (j in 0 until boardSize) {
                    key("cell$i$j") {
                        Column(modifier = Modifier.weight(1f, false)) {
                            if (availablePlays.contains(Pair(i, j)))
                                Cell(currentCell.copy(alpha = .3f), onClick, i, j)
                            else
                                Cell(cells[i][j], onClick, i, j)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(color: Color = Color.Black, onClick: (Int, Int) -> Unit, row: Int, col: Int) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(Color(0xFF81CA9A))
            .clickable { onClick.invoke(row, col) }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .drawWithCache {
                onDrawBehind {
                    drawCircle(color)
                }
            })
    }
}

@Preview
@Composable
fun BoardUiPreview() {
    val cells = listOf(
        listOf(Color.Black, Color.White),
        listOf(Color.White, Color.Black)
    )
    BoardUi(cells, listOf(Pair(0, 0)), Color.Black) { _, _ -> }
}

