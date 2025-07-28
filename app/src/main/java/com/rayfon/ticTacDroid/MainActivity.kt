package com.rayfon.ticTacDroid

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rayfon.ticTacDroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var game: TicTacToeGame
  private lateinit var gridButtons: Array<Array<Button>>

  private fun initializeGridButtons() {
    gridButtons = arrayOf(
      arrayOf(binding.buttonId0, binding.buttonId1, binding.buttonId2),
      arrayOf(binding.buttonId3, binding.buttonId4, binding.buttonId5),
      arrayOf(binding.buttonId6, binding.buttonId7, binding.buttonId8)
    )
  }

  private fun initializeUI() {
    updateBoardUI()
    updateScoreUI()
    updateTurnIndicatorUI()
    binding.playAgainButton.isEnabled = false
  }

  private fun setupGridClickListener() {
    for (row in 0..2) {
      for (col in 0..2) {
        gridButtons[row][col].setOnClickListener { onClickGridButton(row, col) }
      }
    }
    binding.playAgainButton.setOnClickListener { onClickPlayAgainButton() }
  }

  private fun onClickGridButton(row: Int, col: Int) {
    if (game.makeMove(row, col)) {
        gridButtons[row][col].text = game.currentPlayer.name
        gridButtons[row][col].isEnabled = false
        updateTurnIndicatorUI()
        updateBoardUI()
        updateScoreUI()

        if (game.isGameOver) {
            binding.playAgainButton.isEnabled = true
            for (r in 0..2) {
                for (c in 0..2) {
                    gridButtons[r][c].isEnabled = false
                }
            }
            if (game.isDraw) {
                Toast.makeText(this, "It's a Draw!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Player ${game.winner?.name} wins!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    } else {
        Toast.makeText(this, "Invalid Move", Toast.LENGTH_SHORT).show()
    }
  }

  private fun onClickPlayAgainButton() {
    game.resetGame()
    initializeUI()
  }

  private fun updateBoardUI() {
    for (row in 0..2) {
      for (col in 0..2) {
        val button = gridButtons[row][col]
        val player = game.getCell(row, col)
        button.text = player?.name ?: ""
        button.isEnabled = (player == null && !game.isGameOver)
      }
    }
  }

  private fun updateScoreUI() {
    binding.playerXScore.text = game.playerXScore.toString()
    binding.playerOScore.text = game.playerOScore.toString()
    binding.drawScore.text = game.drawScore.toString()
  }

  private fun updateTurnIndicatorUI() {
    when {
      game.winner != null -> {
        binding.playerTurn.text = "Player ${game.currentPlayer?.name}'s Turn"
      }

      game.isDraw -> {
        binding.playerTurn.text = "It's a Draw"
      }

      else -> {
        binding.playerTurn.text = "Player ${game.currentPlayer?.name}'s Turn"
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initializeGridButtons()
    game = TicTacToeGame()
    initializeUI()
    enableEdgeToEdge()
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(
        systemBars.left,
        systemBars.top,
        systemBars.right,
        systemBars.bottom
      )
      insets
    }
    setupGridClickListener()
  }
}