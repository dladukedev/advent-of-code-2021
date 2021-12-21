package dec21

import kotlin.math.E

data class GameState(
    val currentPlayerTurn: Int = 1,

    val player1Space: Int,
    val player1Score: Int = 0,

    val player2Space: Int,
    val player2Score: Int = 0,

    val dieRollCount: Int = 0,
    val diceLastNumber: Int = 0,
)

fun parseInput(input: String): GameState {
    val lines = input.lines()

    val (_, player1Space) = lines.first().split(": ")
    val (_, player2Space) = lines.last().split(": ")

    return GameState(player1Space = player1Space.toInt(), player2Space = player2Space.toInt())
}

fun nextSpace(currentPosition: Int, distance: Int): Int {
    return when (val x = ((distance + currentPosition) % 10)) {
        0 -> {
            10
        }
        else -> {
            x
        }
    }
}

tailrec fun runGame(state: GameState): GameState {
    val die1 = ((state.diceLastNumber + 1) % 100).let { if (it == 0) 100 else it }
    val die2 = ((state.diceLastNumber + 2) % 100).let { if (it == 0) 100 else it }
    val die3 = ((state.diceLastNumber + 3) % 100).let { if (it == 0) 100 else it }

    val result = die1 + die2 + die3

    val newState = if (state.currentPlayerTurn == 1) {
        val newSpace = nextSpace(state.player1Space, result)
        val newScore = state.player1Score + newSpace

        state.copy(
            currentPlayerTurn = 2,
            player1Score = newScore,
            player1Space = newSpace,
            dieRollCount = state.dieRollCount + 3,
            diceLastNumber = die3,
        )
    } else {
        val newSpace = nextSpace(state.player2Space, result)
        val newScore = state.player2Score + newSpace

        state.copy(
            currentPlayerTurn = 1,
            player2Score = newScore,
            player2Space = newSpace,
            dieRollCount = state.dieRollCount + 3,
            diceLastNumber = die3,
        )
    }

    return if (newState.player1Score >= 1000 || newState.player2Score >= 1000) {
        newState
    } else {
        runGame(newState)
    }
}


fun getQuantumWinCount(input: String): Long {
    val startingState = parseInput(input)
    val result = play2(QGameState(startingState.player1Space, startingState.player2Space))

    return listOf(result.p1Wins, result.p2Wins).maxOrNull() ?: throw Exception("Failed to calc max")
}

fun getWinningResult(input: String): Int {
    val startingState = parseInput(input)

    val finalState = runGame(startingState)

    return if (finalState.player1Score >= 1000) {
        finalState.player2Score * finalState.dieRollCount
    } else {
        finalState.player1Score * finalState.dieRollCount
    }
}

data class QGameState(
    val pos1: Int,
    val pos2: Int,
    val score1: Int = 0,
    val score2: Int = 0,
)
data class QGameResult(
    val p1Wins: Long,
    val p2Wins: Long,
)

object CachedResults {
    val cache = mutableMapOf<QGameState, QGameResult>()
}

// Help from - https://www.reddit.com/r/adventofcode/comments/rl6p8y/comment/hpe8pmy/?utm_source=share&utm_medium=web2x&context=3
fun play2(state: QGameState): QGameResult {
    CachedResults.cache[state]?.let {
        return it
    }

    val moves = mutableMapOf(
        3 to 1,
        4 to 3,
        5 to 6,
        6 to 7,
        7 to 6,
        8 to 3,
        9 to 1,
    )

    if(state.score2 >= 21) {
        return QGameResult(0, 1)
    }

    var wins1 = 0L
    var wins2 = 0L
    moves.forEach { move ->
        val newPos1 = nextSpace(move.key, state.pos1)

        val updatedState = QGameState(
            state.pos2,
            newPos1,
            state.score2,
            state.score1 + newPos1
        )
        val result = play2(updatedState)
        wins1 +=  result.p2Wins * move.value
        wins2 += result.p1Wins * move.value

    }
    return QGameResult(wins1, wins2)
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getWinningResult(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getQuantumWinCount(input)
    println("result: $result2")
}