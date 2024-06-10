package com.afedare.reversi_kt

class Board {
    private var currentPlayer = Player.WHITE
    private var board: Array<Array<Int>>

    constructor() : this(Array(BOARD_SIZE) { row ->
        Array(BOARD_SIZE) { col ->
            val indexPair = Pair(row, col)
            when (indexPair) {
                positionToIndices("D5"), positionToIndices("E4") -> Player.BLACK
                positionToIndices("D4"), positionToIndices("E5") -> Player.WHITE
                else -> EMPTY_STATE
            }
        }
    }, Player.WHITE)

    constructor(board: Array<Array<Int>>, currentPlayer: Int) {
        this.board = board
        this.currentPlayer = currentPlayer
    }

    fun nextPlay(choosePlay: (Int, List<Pair<Int, Int>>) -> Pair<Int, Int>): Int {
        val (row, col) = choosePlay(currentPlayer, availablePlays())
        val winner = makeMove(row, col, this)

        return winner
    }

    fun playNewBoard(row: Int, col: Int): Pair<Board, Int> {
        val board = Board(board.map { it.clone() }.toTypedArray(), currentPlayer)
        val winner = board.makeMove(row, col, board)
        return Pair(board, winner)
    }

    private fun makeMove(
        row: Int,
        col: Int,
        board: Board
    ): Int {
        val positionCaptured: List<CaptureDirection> = board.positionCaptured(row, col)

        if (positionCaptured.isNotEmpty()) {
            for (cap in positionCaptured) {
                var i = row
                var j = col
                do {
                    board.board[i][j] = currentPlayer
                    i += cap.stepX
                    j += cap.stepY
                } while (i - cap.row != 0 || j - cap.col != 0)
            }
            return board.checkWinner()
        } else {
            return Winner.NO_WINNER
        }
    }

    fun at(row: Int, col: Int): Int {
        return board[row][col]
    }

    fun availablePlays(player: Int = currentPlayer): List<Pair<Int, Int>> {
        val list = arrayListOf<Pair<Int, Int>>()
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                val positions = positionCaptured(i, j, player)
                if (positions.isNotEmpty()) {
                    list.add(Pair(i, j))
                }
            }
        }
        return list
    }

    fun currentPlayer() = currentPlayer

    fun count(player: Int): Int {
        var count = 0
        for (i in board.indices) {
            for (j in 0 until board[0].size) {
                if (board[i][j] == player) {
                    count++
                }
            }
        }
        return count
    }

    fun winner() = checkWinner()

    fun inversePlayer(player: Int = currentPlayer) = if (player == Player.BLACK) Player.WHITE else Player.BLACK

    fun displayBoard(): String {
        val builder = StringBuilder("  A B C D E F G H\n")

        for (i in board.indices) {
            builder.append("${i + 1} ")
            for (j in board[i].indices) {
                builder.append("${board[i][j]} ")
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    companion object {
        const val BOARD_SIZE: Int = 8
        private const val EMPTY_STATE = 0

        private fun Board.checkWinner(): Int {
            var playerHasOptions = false
            var oppPlayerHasOptions = false
            var blackCount = 0
            var whiteCount = 0

            val player = currentPlayer
            val opponent = inversePlayer(player)

            for (i in board.indices) {
                for (j in board.indices) {
                    val playOpt = positionCaptured(i, j, player)
                    val oppOpt = positionCaptured(i, j, opponent)
                    if (playOpt.isNotEmpty())
                        playerHasOptions = true
                    if (oppOpt.isNotEmpty())
                        oppPlayerHasOptions = true

                    if (board[i][j] == Player.WHITE)
                        whiteCount++
                    else if (board[i][j] == Player.BLACK)
                        blackCount++
                }
            }
            if (!oppPlayerHasOptions && !playerHasOptions) {
                return if (blackCount > whiteCount) Winner.BLACK
                else if (whiteCount > blackCount) Winner.WHITE
                else Winner.DRAW
            } else if (!oppPlayerHasOptions && playerHasOptions) {
                return Winner.NO_WINNER
            } else {
                currentPlayer = inversePlayer()
                return Winner.NO_WINNER
            }
        }

        private fun Board.positionCaptured(
            row: Int,
            col: Int,
            player: Int = currentPlayer
        ): List<CaptureDirection> {
            if (board[row][col] != EMPTY_STATE) return emptyList()
            val opponent = inversePlayer(player)

            val directions = listOf(
                Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1), // row and col
                Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1) // diagonal
            )
            val captures = mutableListOf<CaptureDirection>()
            for (direction in directions) {
                var r = row + direction.first
                var c = col + direction.second
                var hasOpponent = false

                while (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE && board[r][c] == opponent) {
                    r += direction.first
                    c += direction.second
                    hasOpponent = true
                }
                val validDirection =
                    hasOpponent && r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE && board[r][c] == player
                if (validDirection) {
                    captures.add(CaptureDirection(r, c, direction.first, direction.second))
                }
            }
            return captures
        }

    }
}

object Player {
    const val BLACK = 1
    const val WHITE = 2
}

object Winner {
    const val BLACK = Player.BLACK
    const val WHITE = Player.WHITE
    const val NO_WINNER = 3
    const val DRAW = 4
}

data class CaptureDirection(val row: Int, val col: Int, val stepX: Int, val stepY: Int)

fun positionToIndices(position: String): Pair<Int, Int> {
    if (position.length != 2) throw IllegalArgumentException("position length")
    val column = position[0].uppercaseChar()
    val row = position[1]
    if (column !in 'A'..'H' || row !in '1'..'8')
        throw IllegalArgumentException("Position out of bounds")

    val columnIndex = column - 'A'
    val rowIndex = row - '1'
    return Pair(rowIndex, columnIndex)
}

fun indexToPosition(i: Int, j: Int): String {
    val col = 'A' + j
    val row = i + 1
    return "$col$row"
}
