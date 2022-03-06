package com.example.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(letter: String) = when (correctGuesses.contains(letter)) {
        true -> letter
        false -> "_"
    }

    fun isLost(): Boolean = livesLeft == 0

    fun isWon(): Boolean = secretWord.equals(secretWordDisplay, true)


    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) {
            message = "You won!"
        } else if (isLost()) {
            message = "You lost!"
        }
        message += " The word was $secretWord"
        return message
    }

    fun makeGuess(guess: String) {
        //guess is correct
        if (guess.length == 1) {
            if (guess in secretWord.uppercase()) {
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()

            } else  //guess is incorrect
            {
                incorrectGuesses += guess
                livesLeft -= 1
            }
        }
    }
}