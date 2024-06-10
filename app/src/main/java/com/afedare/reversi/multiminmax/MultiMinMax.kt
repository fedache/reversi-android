package com.afedare.reversi.multiminmax

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afedare.reversi.R
import com.afedare.reversi.board.BoardUi

@Composable
fun MultiMinMaxUi() {
    val boardViewModel: MinMaxViewModel = viewModel(
        factory = MinMaxViewModel.provideFactory()
    )
    val boardState by boardViewModel.boardState.collectAsState()
    val agentLoading by boardViewModel.agentLoading.collectAsState()
    Column {
        Row {
            Text(
                stringResource(R.string.player, stringResource(boardState.currentPlayerLabel)),
                Modifier
                    .wrapContentSize(Alignment.CenterStart)
                    .padding(start = 16.dp, top = 16.dp, end = 14.dp)
            )
            if (agentLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(40.dp)
                        .padding(top = 16.dp, end = 14.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Text(
            stringResource(R.string.black_count, boardState.blackCount),
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
        )
        Text(
            stringResource(R.string.white_count, boardState.whiteCount),
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
        )
        if (stringResource(boardState.winner) != "") {
            Text(
                stringResource(boardState.winner),
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            )
        }
        BoardUi(boardState.colors, boardState.availablePlays, boardState.currentPlayer) { i, j ->
            boardViewModel.play(i, j)
        }
    }
}