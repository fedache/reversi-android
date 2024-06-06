package com.afedare.reversi_kt

class Board {
    val size: Int = 8
    private var currentPlayer = BLACK

    private var board: Array<Array<Int>> = Array(size) { row ->
        Array(size) { col ->
            val indexPair = Pair(row, col)
            when (indexPair) {
                positionToIndices("D5"), positionToIndices("E4") -> BLACK
                positionToIndices("D4"), positionToIndices("E5") -> WHITE
                else -> EMPTY_STATE
            }
        }
    }

    constructor() {}

    constructor(board: Array<Array<Int>>, currentPlayer: Int = BLACK) {
        this.board = board
        this.currentPlayer = currentPlayer
    }

    fun skip() {
        if (availablePlays(currentPlayer).isEmpty()) {
            currentPlayer = inversePlayer()
        }
    }

    fun play(position: String) {
        if (makeMovePos(position, board))
            currentPlayer = inversePlayer()
    }

    fun play(row: Int, col: Int) {
        if (makeMove(row, col, board))
            currentPlayer = inversePlayer()
    }

    fun playNewBoard(pair: Pair<Int,Int>): Board {
        val board = board.map { it.clone() }.toTypedArray()
        if (makeMove(pair.first, pair.second, board)) {
            val currentPlayer = inversePlayer()
            return Board(board, currentPlayer)
        } else {
            return this
        }
    }

    private fun makeMovePos(position: String, board: Array<Array<Int>>): Boolean {
        val (row, col) = positionToIndices(position)
        return makeMove(row, col, board)
    }

    private fun makeMove(
        row: Int,
        col: Int,
        board: Array<Array<Int>>
    ): Boolean {
        val positionCaptured: List<CaptureDirection> = positionCaptured(row, col)

        if (positionCaptured.isNotEmpty()) {
            for (cap in positionCaptured) {
                var i = row
                var j = col
                do {
                    board[i][j] = currentPlayer
                    i += cap.stepX
                    j += cap.stepY
                } while (i - cap.row != 0 || j - cap.col != 0)
            }
            return true
        }
        return false
    }

    fun at(row: Int, col: Int): Int {
        return board[row][col]
    }

    fun availablePlays(player: Int = currentPlayer): List<Pair<Int, Int>> {
        val list = arrayListOf<Pair<Int, Int>>()
        for (i in 0 until size) {
            for (j in 0 until size) {
                val positions = positionCaptured(i, j, player)
                if (positions.isNotEmpty()) {
                    list.add(Pair(i, j))
                }
            }
        }
        return list
    }

    fun currentPlayer() = currentPlayer

    fun count(): Pair<Int, Int> {
        var blackCount = 0
        var whiteCount = 0
        for (i in board.indices) {
            for (j in 0 until board[0].size) {
                if (board[i][j] == BLACK) {
                    blackCount++
                } else if (board[i][j] == WHITE) {
                    whiteCount++
                }
            }
        }
        return Pair(blackCount, whiteCount)
    }

    fun winner(): Player {
        val (blackCount, whiteCount) = count()
        for (i in board.indices) {
            for (j in board.indices) {
                val player = currentPlayer
                val opponent = inversePlayer(player)

                if (positionCaptured(i, j, player).isNotEmpty() ||
                    positionCaptured(i, j, opponent).isNotEmpty()
                ) {
                    return Player.NO_WINNER
                }
            }
        }
        return if (blackCount > whiteCount) Player.BLACK
        else if (whiteCount > blackCount) Player.WHITE
        else Player.DRAW
    }

    fun inversePlayer(player: Int = currentPlayer) = if (player == BLACK) WHITE else BLACK

    private fun positionCaptured(
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

            while (r in 0 until size && c in 0 until size && board[r][c] == opponent) {
                r += direction.first
                c += direction.second
                hasOpponent = true
            }
            val validDirection =
                hasOpponent && r in 0 until size && c in 0 until size && board[r][c] == player
            if (validDirection) {
                captures.add(CaptureDirection(r, c, direction.first, direction.second))
            }
        }
        return captures
    }

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
        private const val EMPTY_STATE = 0

        const val BLACK = 1
        const val WHITE = 2
    }
}

enum class Player {
    BLACK,
    WHITE,
    NO_WINNER,
    DRAW
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
