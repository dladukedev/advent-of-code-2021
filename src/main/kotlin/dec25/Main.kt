package dec25

data class Input (
    val startMap: Map<Location, Char>,
    val maxX: Int,
    val maxY: Int,
)

data class Location(
    val x: Int,
    val y: Int,
)

fun parseInput(input: String): Input {
    val lines = input.lines()

    val startMap = lines
        .flatMapIndexed { indexY, line ->
            line.toCharArray().mapIndexed { indexX, char ->
                if(char != '.') {
                    Location(indexX, indexY) to char
                } else {
                    null
                }
            }
        }.filterNotNull()
        .toMap()

    val maxY = lines.size - 1
    val maxX = lines[0].length - 1

    return Input(startMap, maxX, maxY)
}

fun runIteration(input: Map<Location, Char>, maxX:Int, maxY:Int): Map<Location, Char> {
    val leftLocs = input.toList().filter { it.second == '>' }

    val next = mutableMapOf<Location, Char>()

    leftLocs.forEach { pair ->
        val (loc) = pair

        val nextLoc = if(loc.x == maxX) {
            Location(0, loc.y)
        } else {
            Location (loc.x +1, loc.y)
        }

        if(input[nextLoc] == null) {
            next[nextLoc] = '>'
        } else {
            next[loc] = '>'
        }
    }

    val downLocs = input.toList().filter { it.second == 'v' }

    downLocs.forEach { pair ->
        val (loc) = pair
        val nextLoc = if(loc.y == maxY) {
            Location(loc.x, 0)
        } else {
            Location (loc.x, loc.y+1)
        }

        if(input[nextLoc] != 'v' && next[nextLoc] != '>') {
            next[nextLoc] = 'v'
        } else {
            next[loc] = 'v'
        }
    }

    return next
}

tailrec fun runIterationToStop(current: Map<Location, Char>, maxX: Int, maxY: Int, iteration: Int = 1): Int {
    val next = runIteration(current, maxX, maxY)

    return if(next == current) {
        iteration
    } else {
        runIterationToStop(next, maxX, maxY, iteration + 1)
    }

}

fun getStopPoint(input: String): Int {
    val (start, maxX, maxY) = parseInput(input)

    return runIterationToStop(start, maxX, maxY)
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getStopPoint(input)
    println("result: $result1")
}