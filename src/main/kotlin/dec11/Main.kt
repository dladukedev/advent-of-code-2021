package dec11

fun parseInput(input: String): HashMap<Pair<Int, Int>, Octopus> {
    val luminescenceValues = input
        .lines()
        .filter { it.isNotBlank() }
        .map { it.toCharArray().map { char -> char.toString().toInt() } }

    return luminescenceValues.indices.flatMap { col ->
        luminescenceValues.first().indices.map { row ->
            Pair(row,col)
        }
    }.fold(hashMapOf()) { acc, pair ->
        val (row, col) = pair
        acc[pair] = Octopus( luminescenceValues[row][col])

        acc
    }
}

data class Octopus(
    var luminescence: Int,
    var hasFlashed: Boolean = false
)

data class OctopusGarden(
    val garden: HashMap<Pair<Int, Int>, Octopus>
) {
    private fun getOrNull(row: Int, col: Int): Octopus? {
        return garden[Pair(row, col)]
    }

    operator fun get(row: Int, col: Int): Octopus {
        return getOrNull(row, col) ?: throw Exception("No value at $row, $col")
    }

    fun getAdjacentPoints(row: Int, col: Int): List<Octopus> {
        return listOfNotNull(
            getOrNull(row - 1, col),
            getOrNull(row, col - 1),
            getOrNull(row + 1, col),
            getOrNull(row, col + 1),
            getOrNull(row - 1, col - 1),
            getOrNull(row + 1, col + 1),
            getOrNull(row - 1, col + 1),
            getOrNull(row + 1, col - 1),
        )
    }
}

tailrec fun processRound(octopusGarden: OctopusGarden): OctopusGarden {
    val octopusesToFlash = octopusGarden.garden.filter {
        val octopus = it.value
        octopus.luminescence > 9 && !octopus.hasFlashed
    }
    
    return if(octopusesToFlash.isNotEmpty()) {

        // Do flashes
        octopusesToFlash.forEach { pair ->
            val adjacentOctopuses = octopusGarden.getAdjacentPoints(pair.key.first, pair.key.second)
            adjacentOctopuses.forEach { octopus ->
                octopus.luminescence += 1
            }

            pair.value.hasFlashed = true
        }

        processRound(octopusGarden)
    } else {
        octopusGarden
    }

}

tailrec fun countFlashes(octopusGarden: OctopusGarden, steps: Int, flashes: Long = 0): Long {
    if(steps == 0) {
        return flashes
    }

    octopusGarden.garden.forEach {
        it.value.luminescence += 1
    }

    val gardenAfterRound = processRound(octopusGarden)

    val roundFlashCount = gardenAfterRound.garden.count { it.value.hasFlashed }

    gardenAfterRound.garden.forEach {
        if(it.value.hasFlashed) {
            it.value.luminescence = 0
        }

        it.value.hasFlashed = false
    }

    return countFlashes(octopusGarden, steps - 1, flashes + roundFlashCount)
}


tailrec fun findFullFlashRound(octopusGarden: OctopusGarden, stepsElapsed: Long = 1): Long {
    octopusGarden.garden.forEach {
        it.value.luminescence += 1
    }

    val gardenAfterRound = processRound(octopusGarden)

    val roundFlashCount = gardenAfterRound.garden.count { it.value.hasFlashed }

    gardenAfterRound.garden.forEach {
        if(it.value.hasFlashed) {
            it.value.luminescence = 0
        }

        it.value.hasFlashed = false
    }

    if(roundFlashCount == octopusGarden.garden.size) {
        return stepsElapsed
    }

    return findFullFlashRound(octopusGarden, stepsElapsed + 1)
}

fun getFlashCount(input: String, steps: Int): Long {
    val octopusGarden = OctopusGarden(parseInput(input))

    return countFlashes(octopusGarden, steps)
}

fun getFirstFullFlashRound(input: String): Long {
    val octopusGarden = OctopusGarden(parseInput(input))

    return findFullFlashRound(octopusGarden)
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getFlashCount(input, 100)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getFirstFullFlashRound(input)
    println("result: $result2")
}