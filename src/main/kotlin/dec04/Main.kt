package dec04

data class BoardSpace(
    val value: Int,
) {
    val isMarked: Boolean
        get() = _isMarked
    private var _isMarked: Boolean = false

    fun called(calledValue: Int) {
        if(calledValue == value) {
            _isMarked = true
        }
    }
}

data class Board(
    val spaces: List<List<BoardSpace>>
) {
    val hasWin: Boolean
        get() {
            val board = this
            val rowWin = spaces.any { row -> row.all { space -> space.isMarked } }
            val colWin = spaces.indices.any { colIndex -> spaces.indices.all { rowIndex -> board[rowIndex,colIndex].isMarked } }

            return rowWin || colWin
        }

    fun getSumUnmarkedSpaces(): Int {
        return spaces
            .flatten()
            .sumOf { space -> if(space.isMarked) 0 else space.value  }
    }

    fun handleCall(calledValue: Int) {
        spaces.forEach { row ->
            row.forEach { space ->
                space.called(calledValue)
            }
        }
    }

    operator fun get(colIndex: Int, rowIndex: Int): BoardSpace = spaces[colIndex][rowIndex]
}

fun getFinalScore(calls: List<Int>, boardsInput: List<String>): Int {
    val boards = parseInputToBoards(boardsInput)

    for (call in calls) {
        boards.forEach { board -> board.handleCall(call) }

        val winner = boards.find { board -> board.hasWin }

        if(winner != null) {
            println("call: $call")
            println("spaces: ${winner.getSumUnmarkedSpaces()}")
            return winner.getSumUnmarkedSpaces() * call
        }
    }

    throw Exception("No Winning Board Found")
}

fun getLastFinalScore(calls: List<Int>, boardsInput: List<String>): Int {
    var boards = parseInputToBoards(boardsInput)

    for (call in calls) {
        boards.forEach { board -> board.handleCall(call) }

        val winners = boards.filter { board -> board.hasWin }

        if(boards.size == 1 && winners.size == 1) {
            val winner = winners.single()
            println("call: $call")
            println("spaces: ${winner.getSumUnmarkedSpaces()}")
            return winner.getSumUnmarkedSpaces() * call
        } else {
            // Remove Winners
            boards = boards.filter { board -> !board.hasWin }
        }

    }

    throw Exception("No Winning Board Found")
}

fun parseInputToBoards(input: List<String>): List<Board> {
    return input.map { rowStr ->
        val boardSpaces: List<List<BoardSpace>> = rowStr.lines()
            .map { row -> 
                row.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.trim() }
                    .map { 
                        BoardSpace(it.toInt())
                    }
            }
        
        Board(boardSpaces)
    }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getFinalScore(inputCalls, inputBoards)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getLastFinalScore(inputCalls, inputBoards)
    println("result: $result2")
}