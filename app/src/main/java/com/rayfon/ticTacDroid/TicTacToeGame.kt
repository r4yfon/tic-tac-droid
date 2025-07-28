package com.rayfon.ticTacDroid

import java.io.Serializable

enum class Player { X, O }

class TicTacToeGame : Serializable {
  private var board: Array<Array<Player?>> = Array(3) { Array(3) { null } }

  var currentPlayer: Player = Player.X
  var winner: Player? = null
  var playerXScore: Int = 0
  var playerOScore: Int = 0
  var isDraw: Boolean = false
  var isGameOver: Boolean = false
  var drawScore: Int = 0

  private fun switchPlayer() {
    currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
  }

  private fun checkWinCondition(row: Int, col: Int): Boolean {
    val player = board[row][col] ?: return false

    if (board[row][0] == player && board[row][1] == player && board[row][2] == player) return true
    if (board[0][col] == player && board[1][col] == player && board[2][col] == player) return true
    if (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
    if (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) return true

    return false
  }

  private fun boardIsFull(): Boolean {
    for (row in 0..2) {
      for (col in 0..2) {
        if (board[row][col] == null) {
          return false
        }
      }
    }
    return true
  }

  fun resetGame() {
    board = Array(3) { arrayOfNulls(3) }
    currentPlayer = Player.X
    winner = null
    isDraw = false
    isGameOver = false
  }

  fun makeMove(row: Int, col: Int): Boolean {
    if (row !in 0..2 || col !in 0..2 || board[row][col] != null || isGameOver) {
      return false
    }

    board[row][col] = currentPlayer
    if (checkWinCondition(row, col)) {
      winner = currentPlayer
      isGameOver = true
      if (currentPlayer == Player.X) {
        playerXScore++
      } else {
        playerOScore++
      }
    } else if (boardIsFull()) {
      isDraw = true
      isGameOver = true
      drawScore++
    } else {
      switchPlayer()
    }
    return true
  }

  fun getCell(row: Int, col: Int): Player? {
    if (row in 0..2 && col in 0..2) {
      return board[row][col]
    }
    return null
  }
}
