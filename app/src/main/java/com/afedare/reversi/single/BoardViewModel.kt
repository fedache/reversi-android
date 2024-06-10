package com.afedare.reversi.single

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afedare.reversi.R
import com.afedare.reversi_kt.Board
import com.afedare.reversi_kt.Player
import com.afedare.reversi_kt.Winner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel : ViewModel() {
    val boardState: MutableStateFlow<BoardState>
    private val board = Board()

    init {
        boardState = MutableStateFlow(board.toBoardState(board.winner()))
    }


    fun play(row: Int, col: Int) {
        val winner = board.nextPlay { _, _ -> Pair(row, col) }
        boardState.update {
            board.toBoardState(winner)
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

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BoardViewModel() as T
            }
        }

        fun Board.toBoardState(winner: Int): BoardState {
            val boardSize = Board.BOARD_SIZE
            val blackCount = count(Player.BLACK)
            val whiteCount = count(Player.WHITE)
            return BoardState(
                List(boardSize) { i ->
                    List(boardSize) { j ->
                        val cell = at(i, j)
                        boardColor(cell)
                    }
                },
                boardColor(currentPlayer()),
                playerLabel(currentPlayer()),
                blackCount,
                whiteCount,
                winnerLabel(winner),
                availablePlays()
            )
        }

        private fun boardColor(cell: Int) =
            if (cell == Player.BLACK) Color.Black else if (cell == Player.WHITE) Color.White else Color.Unspecified

        private fun playerLabel(cell: Int) =
            if (cell == Player.BLACK) R.string.black_label
            else if (cell == Player.WHITE) R.string.white_label
            else R.string.empty

        private fun winnerLabel(winner: Int): Int {
            return if (winner == Winner.NO_WINNER) R.string.empty
            else if (winner == Winner.BLACK) R.string.black_wins
            else if (winner == Winner.WHITE) R.string.white_wins
            else if (winner == Winner.DRAW) R.string.draw
            else R.string.empty
        }
    }
}