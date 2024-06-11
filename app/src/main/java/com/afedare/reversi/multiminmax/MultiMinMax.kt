package com.afedare.reversi.multiminmax

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.afedare.reversi.board.BoardStatus
import com.afedare.reversi.board.BoardUi

@Composable
fun MultiMinMaxUi() {
    val boardViewModel: MinMaxViewModel = viewModel(
        factory = MinMaxViewModel.provideFactory()
    )
    val boardState by boardViewModel.boardState.collectAsState()
    val agentLoading by boardViewModel.agentLoading.collectAsState()
    Column {
        if (stringResource(boardState.winner) != "") {
            Text(
                stringResource(boardState.winner),
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            )
        }
        Column(Modifier.align(Alignment.CenterHorizontally).padding(4.dp, 16.dp)) {
            BoardStatus(boardState)
            if (agentLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(40.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        BoardUi(boardState.colors, boardState.availablePlays, boardState.currentPlayer) { i, j ->
            boardViewModel.play(i, j)
        }
    }
}