package com.afedare.reversi.service

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afedare.reversi.R
import com.afedare.reversi_kt.Board
import com.afedare.reversi_kt.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel : ViewModel() {
    val boardState: MutableStateFlow<BoardState>
    private val board = Board()

    init {
        val boardSize = 8
        val (blackCount, whiteCount) = board.count()
        boardState = MutableStateFlow(
            BoardState(
                List(boardSize) { i ->
                    List(boardSize) { j ->
                        val cell = board.at(i, j)
                        boardColor(cell)
                    }
                },
                boardColor(board.currentPlayer()),
                playerLabel(board.currentPlayer()),
                blackCount,
                whiteCount,
                winnerLabel(),
                board.availablePlays()
            )
        )
    }

    private fun boardColor(cell: Int) =
        if (cell == Board.BLACK) Color.Black else if (cell == Board.WHITE) Color.White else Color.Unspecified

    private fun playerLabel(cell: Int) =
        if (cell == Board.BLACK) R.string.black_label
        else if (cell == Board.WHITE) R.string.white_label
        else R.string.empty

    private fun winnerLabel(): Int {
        val winner = board.winner()
        return if (winner == Player.NO_WINNER) R.string.empty
        else if (winner == Player.BLACK) R.string.black_wins
        else if (winner == Player.WHITE) R.string.white_wins
        else if (winner == Player.DRAW) R.string.draw
        else R.string.empty
    }

    fun play(row: Int, col: Int) {
        board.play(row, col)
        val boardList = List<List<Color>>(board.size) { i ->
            List(board.size) { j -> boardColor(board.at(i, j))}
        }

        boardState.update {
            val (blackCount, whiteCount) = board.count()
            BoardState(
                boardList,
                boardColor(board.currentPlayer()),
                playerLabel(board.currentPlayer()),
                blackCount,
                whiteCount,
                winnerLabel(),
                board.availablePlays()
            )
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BoardViewModel() as T
            }
        }
    }

    data class BoardState(
        val colors: List<List<Color>>,
        val currentPlayer: Color,
        @StringRes val currentPlayerLabel: Int,
        val blackCount: Int,
        val whiteCount: Int,
        @StringRes val winner: Int,
        val availablePlays: List<Pair<Int, Int>>
    )
}