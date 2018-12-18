package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val width = 4
    private val board = createGameBoard<Int?>(width)

    override fun initialize() {
        val allCells = board.getAllCells()
        initializer.initialPermutation.forEachIndexed { index, i ->
            board[allCells.elementAt(index)] = i
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon(): Boolean {
        return (1..15).toList() == board.getAllCells().map { get(it.i, it.j) }.toList().filterNotNull()
    }

    override fun processMove(direction: Direction) {
        val nullCell = board.getAllCells().first { board[it] == null }

        when (direction) {
            Direction.DOWN -> {
                board.getCellOrNull(nullCell.i-1, nullCell.j)?.let { swap(it, nullCell) }
            }
            Direction.UP -> {
                board.getCellOrNull(nullCell.i+1, nullCell.j)?.let { swap(it, nullCell) }
            }
            Direction.LEFT -> {
                board.getCellOrNull(nullCell.i, nullCell.j+1)?.let { swap(it, nullCell) }
            }
            Direction.RIGHT -> {
                board.getCellOrNull(nullCell.i, nullCell.j-1)?.let { swap(it, nullCell) }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

    private fun swap(cell1: Cell, cell2: Cell) {
        val initialValue = board[cell1]
        board[cell1] = board[cell2]
        board[cell2] = initialValue
    }
}