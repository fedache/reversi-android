package com.afedare.reversi_kt

import com.afedare.reversi_kt.Board.Companion.BLACK
import com.afedare.reversi_kt.Board.Companion.WHITE
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
    fun testBoardPlays() {
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
            ), WHITE
        )
        println(board.displayBoard())
        board.play("F7")
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
            ), BLACK
        )
        println(board.displayBoard())
        board.play("D3")
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
            ), BLACK
        )
        println(board.displayBoard())
        val newBoard = board.playNewBoard("D3")
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
}