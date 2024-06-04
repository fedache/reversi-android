package com.afedare.reversi_kt

import kotlin.random.Random

object RandomAgent {
    fun play() {
        val board = Board()
        while (board.winner() == Player.NO_WINNER) {
            val currentPlayer = board.currentPlayer()
            val availablePlays = board.availablePlays(currentPlayer)
            if (availablePlays.isNotEmpty()) {
                println(availablePlays)
                val randomPlay = Random.nextInt(0, availablePlays.size)
                println("Player $currentPlayer plays:  ${availablePlays[randomPlay]}")
                board.play(availablePlays[randomPlay])
                println(board.displayBoard())
            } else {
                board.skip()
            }
        }
        println("Winner ${board.winner()}")
    }
}