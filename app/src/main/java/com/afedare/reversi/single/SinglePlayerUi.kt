package com.afedare.reversi.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afedare.reversi.R
import com.afedare.reversi.board.BoardUi

@Composable
fun SinglePlayerUi() {
    val boardViewModel: BoardViewModel = viewModel(
        factory = BoardViewModel.provideFactory()
    )
    val boardState by boardViewModel.boardState.collectAsState()
    Column {
        Text(
            stringResource(R.string.player, stringResource(boardState.currentPlayerLabel)),
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
        )
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