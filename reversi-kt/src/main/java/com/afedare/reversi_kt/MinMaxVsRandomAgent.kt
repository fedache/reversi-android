package com.afedare.reversi_kt

import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.measureTime


object MinMaxVsRandomAgent {
    fun play() {
        val N = 20
        var minmaxWins = 0
        var randomWins = 0
        var draws = 0
        val timeTaken = measureTime {
            for (i in 0 until N) {
                println("Iteration $i")
                val board = Board()
                val minMaxPlayer = Random.nextInt(Player.BLACK..Player.WHITE)

                while (true) {
                    val winner = board.nextPlay { currentPlayer, availablePlays ->
                        println("Player $currentPlayer")
                        println(board.displayBoard())
                        if (currentPlayer == minMaxPlayer) {
                            minMax(board, availablePlays, depth = 3)
                        } else {
                            val randomIndex = Random.nextInt(0, availablePlays.size)
                            availablePlays[randomIndex]
                        }
                    }
                    if (winner != Winner.NO_WINNER) {
                        if (minMaxPlayer == winner)
                            minmaxWins++
                        else if (winner == Winner.DRAW)
                            draws++
                        else
                            randomWins++
                        println("Winner $winner")
                        println("MinMax Player: $minMaxPlayer")
                        break
                    }
                }
            }
        }
        println("Time taken: $timeTaken")
        println("Minmax wins: $minmaxWins")
        println("Random wins: $randomWins")
        println("Draws: $draws")
    }
}

fun minMax(board: Board, initAvailablePlays: List<Pair<Int, Int>>, depth: Int = 4): Pair<Int, Int> {
    var maxScore: Int = Int.MIN_VALUE
    var bestPlay = initAvailablePlays.first()
    for (play in initAvailablePlays) {
        val (row, col) = play
        val (newBoard, winner) = board.playNewBoard(row, col)
        val score = minMax(newBoard, winner, depth, false)
        if (score > maxScore) {
            maxScore = score
            bestPlay = play
        }
    }
    return bestPlay
}

fun minMax(
    board: Board,
    winner: Int,
    depth: Int,
    isMaxingPlayer: Boolean,
    alpha: Int = Int.MIN_VALUE,
    beta: Int = Int.MAX_VALUE
): Int {
    if (depth == 0 || winner != Winner.NO_WINNER) return evaluate(board, isMaxingPlayer)
    val availablePlays = board.availablePlays()
    if (isMaxingPlayer) {
        var maxEval: Int = Int.MIN_VALUE
        for (i in 0 until availablePlays.size) {
            val (row, col) = availablePlays[i]
            val (newBoard, newWinner) = board.playNewBoard(row, col)
            val score = minMax(newBoard, newWinner,depth - 1, false)
            maxEval = Math.max(maxEval, score)
            if (beta <= Math.max(alpha, maxEval))
                break
        }
        return maxEval
    } else {
        var minEval: Int = Int.MAX_VALUE
        for (i in 0 until availablePlays.size) {
            val (row, col) = availablePlays[i]
            val (newBoard, newWinner) = board.playNewBoard(row, col)
            val score = minMax(newBoard, newWinner,depth - 1, true)
            minEval = Math.min(minEval, score)
            if (Math.min(beta, minEval) <= alpha)
                break
        }
        return minEval
    }
}

fun evaluate(board: Board, isMaxingPlayer: Boolean): Int {
    val maxingPlayer = if (isMaxingPlayer) board.currentPlayer() else board.inversePlayer()
    val minPlayer = board.inversePlayer(maxingPlayer)
    val countMaxPlayer = board.count(maxingPlayer)
    val countMinPlayer = board.count(minPlayer)
    val countMaxPlays = board.availablePlays(maxingPlayer).size
    val countMinPlays = board.availablePlays(minPlayer).size
    val availablePlaysHeuristic = countMaxPlays - countMinPlays
    val countDiscHeuristic = countMaxPlayer - countMinPlayer

    var positionMaxPlayer = 0
    for (i in 0 until Board.BOARD_SIZE) {
        for (j in 0 until Board.BOARD_SIZE) {
            val cell = board.at(i, j)
            if (cell == maxingPlayer) {
                positionMaxPlayer += staticWeights[i][j]
            }
        }
    }
    val positionHeuristic = positionMaxPlayer
    return (4 * positionHeuristic) + (2 * countDiscHeuristic) + availablePlaysHeuristic
}


fun safeDivide(a: Int, b: Int): Int {
    return try {
        a / b
    } catch (e: ArithmeticException) {
        return 0
    }
}

val staticWeights = arrayOf(
    arrayOf(4, -3, 2, 2, 2, 2, -3, 4),
    arrayOf(-3, -4, -1, -1, -1, -1, -4, -3),
    arrayOf(2, -1, 1, 0, 0, 1, -1, 2),
    arrayOf(2, -1, 0, 1, 1, 0, -1, 2),
    arrayOf(2, -1, 0, 1, 1, 0, -1, 2),
    arrayOf(2, -1, 1, 0, 0, 1, -1, 2),
    arrayOf(-3, -4, -1, -1, -1, -1, -4, -3),
    arrayOf(4, -3, 2, 2, 2, 2, -3, 4)
)