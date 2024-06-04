package com.afedare.reversi_kt

import com.afedare.reversi_kt.Board.Companion.BLACK
import com.afedare.reversi_kt.Board.Companion.WHITE
import kotlin.random.Random


object MinMaxVsRandomAgent {
    fun play() {
        val board = Board()
        while (board.winner() == Player.NO_WINNER) {
            val currentPlayer = board.currentPlayer()
            val availablePlays = board.availablePlays(currentPlayer)
            if (availablePlays.isNotEmpty()) {
                println(availablePlays)
                var chosenPlay: String
                if (currentPlayer == WHITE) {
                    val randomIndex = Random.nextInt(0, availablePlays.size)
                    chosenPlay = availablePlays[randomIndex]
                } else {
                    chosenPlay = minMax(board, availablePlays)
                }
                val score = evaluate(board, currentPlayer == BLACK)
                println("Player $currentPlayer plays:  $chosenPlay, Score: $score")
                board.play(chosenPlay)
                println(board.displayBoard())
            } else {
                board.skip()
            }
        }
        println("Winner ${board.winner()}")
    }
}

fun minMax(board: Board, initAvailablePlays: List<String>): String {
    var maxScore: Int = Int.MIN_VALUE
    var bestPlay: String = initAvailablePlays.first()
    val depth = 4
    println("Depth: $depth")
    for (play in initAvailablePlays) {
        val score = minMax(board.playNewBoard(play), depth, false)
        if (score > maxScore) {
            maxScore = score
            bestPlay = play
        }
    }
    return bestPlay
}

fun minMax(board: Board, depth: Int, isMaxingPlayer: Boolean): Int {
    if (depth == 0 || board.winner() != Player.NO_WINNER)
        return evaluate(board, isMaxingPlayer)

    val availablePlays = board.availablePlays()
    if (isMaxingPlayer) {
        var maxEval: Int = Int.MIN_VALUE
        for (i in 0 until availablePlays.size) {
            val play = availablePlays[i]
            val score = minMax(board.playNewBoard(play), depth - 1, false)
            maxEval = Math.max(maxEval, score)
        }
        return maxEval
    } else {
        var minEval: Int = Int.MAX_VALUE
        for (i in 0 until availablePlays.size) {
            val play = availablePlays[i]
            val score = minMax(board.playNewBoard(play), depth - 1, true)
            minEval = Math.min(minEval, score)
        }
        return minEval
    }
}


fun evaluate(board: Board, isMaxingPlayer: Boolean): Int {
    val player = board.currentPlayer()
    val (blackCount, whiteCount) = board.count()
    return if (player == BLACK && isMaxingPlayer)
        blackCount - whiteCount
    else if (player == BLACK && !isMaxingPlayer)
        -(blackCount - whiteCount)
    else if (player == WHITE && isMaxingPlayer)
        whiteCount - blackCount
    else
        -(whiteCount - blackCount)

}
