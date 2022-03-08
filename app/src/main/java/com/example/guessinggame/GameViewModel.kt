package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()
    val secretWordDisplay = MutableLiveData<String>()
    var correctGuesses = ""
    val incorrectGuesses = MutableLiveData<String>("")
    private val livesLeft = MutableLiveData<Int>(8)
    val _livesLeft: LiveData<Int>
        get() = livesLeft
    private val _gameWon = MutableLiveData<Boolean>(false)
    val gameWon: LiveData<Boolean>
        get() = _gameWon

    init {
        secretWordDisplay.value = deriveSecretWordDisplay()
    }

     fun deriveSecretWordDisplay(): String {
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

    private fun isLost(): Boolean = _livesLeft.value == 0

    private fun isWon(): Boolean = secretWord.equals(secretWordDisplay.value, true)


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
                secretWordDisplay.value = deriveSecretWordDisplay()

            } else  //guess is incorrect
            {
                incorrectGuesses.value += guess
                livesLeft.value = livesLeft.value?.minus(1)
            }
        }
        if (isWon() || isLost()) {
            _gameWon.value = true
        }
    }
}