package com.afedare.reversi_kt

import org.junit.Assert.assertEquals
import org.junit.Test

class BoardTest {
    @Test
    fun testValidPositions() {
        assertEquals(Pair(0, 0), positionToIndices("A1"))
        assertEquals(Pair(2, 0), positionToIndices("A3"))
        assertEquals(Pair(3, 3), positionToIndices("D4"))
    }

    @Test
    fun testBoardPlays__Diagonal() {
        val board = Board(
            arrayOf(
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 1, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 1, 1, 0, 0, 0),
                arrayOf(0, 0, 2, 2, 1, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 1, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            ), Player.WHITE
        )
        println(board.displayBoard())
        board.nextPlay { i, availablePlays ->
            Pair(6, 5)
        }
        assertEquals(
            board.displayBoard(), """  A B C D E F G H
1 0 0 0 0 0 0 0 0 
2 0 0 0 0 0 0 0 0 
3 0 0 0 1 0 0 0 0 
4 0 0 0 1 1 0 0 0 
5 0 0 2 2 1 0 0 0 
6 0 0 0 0 2 0 0 0 
7 0 0 0 0 0 2 0 0 
8 0 0 0 0 0 0 0 0 
"""
        )
    }

    @Test
    fun testBoardPlays__fromInit() {
        val board = Board(
            arrayOf(
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 2, 1, 0, 0, 0),
                arrayOf(0, 0, 0, 1, 2, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            ), Player.BLACK
        )
        println(board.displayBoard())
        board.nextPlay { _, _ ->
            Pair(2, 3)
        }
        assertEquals(
            board.displayBoard(), """  A B C D E F G H
1 0 0 0 0 0 0 0 0 
2 0 0 0 0 0 0 0 0 
3 0 0 0 1 0 0 0 0 
4 0 0 0 1 1 0 0 0 
5 0 0 0 1 2 0 0 0 
6 0 0 0 0 0 0 0 0 
7 0 0 0 0 0 0 0 0 
8 0 0 0 0 0 0 0 0 
"""
        )
    }

    @Test
    fun testBoardPlay__newBoard() {
        val board = Board(
            arrayOf(
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 2, 1, 0, 0, 0),
                arrayOf(0, 0, 0, 1, 2, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            ), Player.BLACK
        )
        println(board.displayBoard())
        val (newBoard, winner) = board.playNewBoard(2, 3)
        assertEquals(
            board.displayBoard(), """  A B C D E F G H
1 0 0 0 0 0 0 0 0 
2 0 0 0 0 0 0 0 0 
3 0 0 0 0 0 0 0 0 
4 0 0 0 2 1 0 0 0 
5 0 0 0 1 2 0 0 0 
6 0 0 0 0 0 0 0 0 
7 0 0 0 0 0 0 0 0 
8 0 0 0 0 0 0 0 0 
"""
        )
        assertEquals(
            newBoard.displayBoard(), """  A B C D E F G H
1 0 0 0 0 0 0 0 0 
2 0 0 0 0 0 0 0 0 
3 0 0 0 1 0 0 0 0 
4 0 0 0 1 1 0 0 0 
5 0 0 0 1 2 0 0 0 
6 0 0 0 0 0 0 0 0 
7 0 0 0 0 0 0 0 0 
8 0 0 0 0 0 0 0 0 
"""
        )
    }


    @Test
    fun testBoardPlay__winner() {
        val board = Board(
            arrayOf(
                arrayOf(2, 2, 2, 1, 0, 0, 0, 0),
                arrayOf(0, 2, 0, 1, 0, 0, 0, 0),
                arrayOf(2, 1, 2, 1, 0, 0, 0, 0),
                arrayOf(0, 1, 0, 1, 1, 0, 0, 0),
                arrayOf(2, 1, 0, 1, 1, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            ), Player.BLACK
        )

        assertEquals(board.winner(), Winner.NO_WINNER)
        assertEquals(board.currentPlayer(), Player.WHITE)
    }

    @Test
    fun testBoardPlay__trappedBlack() {
        val board = Board(
            arrayOf(
                arrayOf(2, 2, 2, 1, 1, 1, 1, 1),
                arrayOf(1, 2, 2, 2, 2, 1, 2, 1),
                arrayOf(1, 2, 2, 1, 2, 1, 2, 1),
                arrayOf(1, 2, 1, 2, 2, 2, 2, 1),
                arrayOf(1, 1, 1, 1, 1, 1, 1, 1),
                arrayOf(1, 0, 1, 0, 0, 1, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            ), Player.WHITE
        )
        val winner  = board.nextPlay { i, availablePlays ->
            Pair(6, 0)
        }
        assertEquals(winner, Winner.NO_WINNER)
        assertEquals(board.currentPlayer(), Player.WHITE)
    }
}