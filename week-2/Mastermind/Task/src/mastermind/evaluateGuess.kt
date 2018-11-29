package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var secretArray = secret.toCharArray()
    var guessArray = guess.toCharArray()
    var rightPosition = 0
    var wrongPosition = 0

    for (i in 0 until secretArray.size) {
        if (secretArray[i] == guessArray[i]){
            rightPosition++
            secretArray[i] = ' '
            guessArray[i] = ' '
        }
    }

    for (i in 0 until secretArray.size) {
        for (j in 0 until guessArray.size) {
            if (secretArray[i] == guessArray[j] && guessArray[j] != ' ') {
                if (i == j) {
                    rightPosition++
                } else {
                    wrongPosition++
                }
                secretArray[i] = ' '
                guessArray[j] = ' '
            }
        }
    }

    return Evaluation(rightPosition, wrongPosition)
}