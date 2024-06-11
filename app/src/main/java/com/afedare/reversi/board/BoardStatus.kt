package com.afedare.reversi.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.afedare.reversi.R
import com.afedare.reversi.single.BoardViewModel

@Composable
fun BoardStatus(boardState: BoardViewModel.BoardState) {
    Row(
        Modifier
            .wrapContentWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .padding(10.dp, 3.dp, 10.dp, 3.dp)
            .drawWithCache {
                onDrawBehind {
                    drawCircle(boardState.currentPlayer)
                }
            })
        Text(
            text = stringResource(R.string.turn),
            Modifier
                .wrapContentWidth()
                .padding(0.dp, 8.dp, 0.dp, 8.dp),
            style = TextStyle(fontSize = 20.sp, color = Color.Black)
        )
        Box(modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .padding(10.dp, 3.dp, 10.dp, 3.dp)
            .drawWithCache {
                onDrawBehind {
                    drawCircle(Color.Black)
                }
            })
        Text(
            text = "%d-%d".format(boardState.blackCount, boardState.whiteCount),
            Modifier
                .wrapContentWidth()
                .padding(0.dp, 8.dp, 0.dp, 8.dp),
            style = TextStyle(fontSize = 20.sp, color = Color.Black)
        )
        Box(modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .padding(10.dp, 3.dp, 10.dp, 3.dp)
            .drawWithCache {
                onDrawBehind {
                    drawCircle(Color.White)
                }
            })
    }
}