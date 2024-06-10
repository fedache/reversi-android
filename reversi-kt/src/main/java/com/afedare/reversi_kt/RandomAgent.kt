package com.afedare.reversi_kt

import kotlin.random.Random

object RandomAgent {
    fun play() {
        val board = Board()
        while (true) {
            val currentPlayer = board.currentPlayer()
            val winner = board.nextPlay { i, availablePlays ->
                val randomPlay = Random.nextInt(0, availablePlays.size)
                println("Player $currentPlayer plays:  ${availablePlays[randomPlay]}")
                return@nextPlay availablePlays[randomPlay]
            }
            if (winner != Winner.NO_WINNER) {
                println("Winner $winner")
                break
            }
            println("Player $currentPlayer")
            println(board.displayBoard())
        }
    }
}