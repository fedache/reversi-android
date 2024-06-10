package com.afedare.reversi.multiminmax

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.afedare.reversi.single.BoardViewModel
import com.afedare.reversi.single.BoardViewModel.Companion.toBoardState
import com.afedare.reversi_kt.Board
import com.afedare.reversi_kt.Player
import com.afedare.reversi_kt.minMax
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class MinMaxViewModel : ViewModel() {
    private val board = Board()
    private val computer =  Player.BLACK
    private val user: Int = Player.WHITE

    val boardState: MutableStateFlow<BoardViewModel.BoardState> =
        MutableStateFlow(board.toBoardState(board.winner()))
    val agentLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        compPlay()
    }

    fun play(row: Int, col: Int) {
        if (user == board.currentPlayer()) {
            val winner = board.nextPlay { _, _ -> Pair(row, col) }
            boardState.update { board.toBoardState(winner) }
        }
        compPlay()
    }

    private fun compPlay() {
        if (computer != board.currentPlayer()) return
        agentLoading.value = true
        val moveDeferred = viewModelScope.async(Dispatchers.IO) {
            return@async minMax(board, board.availablePlays())
        }
        viewModelScope.launch {
            val play = moveDeferred.await()
            val winner = board.nextPlay { _, _ -> play }
            println("board winner: $winner")
            println("board: ${board.displayBoard()}")
            agentLoading.value = false
            boardState.update { board.toBoardState(winner) }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MinMaxViewModel() as T
            }
        }
    }
}