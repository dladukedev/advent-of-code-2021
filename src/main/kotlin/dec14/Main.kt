package dec14

data class Instruction(
    val pair: String,
    val insertCharacter: String,
) {
    val firstInsert = pair.first() + insertCharacter
    val secondInsert =  insertCharacter + pair.last()
}

fun parseInstructions(input: List<String>): List<Instruction> {
    return input.map {
        val (pair, insert) = it.split(" -> ")
        Instruction(pair, insert)
    }
}

tailrec fun applyInstructions(input: HashMap<String, Long>, instructions: List<Instruction>, iterations: Int): HashMap<String, Long> {
    if(iterations == 0) {
        return input
    }

    val updated = hashMapOf<String, Long>()

    input.keys.forEach { key ->
        val instruction = instructions.singleOrNull { it.pair == key }

        if(instruction != null) {
            updated[instruction.firstInsert] = (updated[instruction.firstInsert] ?: 0) + input[key]!!
            updated[instruction.secondInsert] = (updated[instruction.secondInsert] ?: 0) + input[key]!!
        } else {
            updated[key] = input[key]!!
        }
    }

    return applyInstructions(updated, instructions, iterations - 1)
}

fun calculateDifferenceBetweenMostAndLeastCommonDigit(inputStart: String, inputInstructions: List<String>, steps: Int): Long {
    val instructions = parseInstructions(inputInstructions)
    val start = inputStart.toCharArray().toList()
        .windowed(2)
        .map { it.joinToString("") }
        .groupBy { it }
        .map { it.key to it.value.size.toLong() }
        .toMap() as HashMap

    val final = applyInstructions(start, instructions, steps)

    val charCounts = final.toList()
        .fold(mutableMapOf<Char, Long>()) { acc, pair ->
            val (characters, amount) = pair
            val (first, second) = characters.toCharArray()

            acc[first] = (acc[first] ?: 0) + amount
            acc[second] = (acc[second] ?: 0) + amount

            acc
        }.map {
            it.key to (it.value + 1) / 2
        }

    val max = charCounts.maxOf { it.second }
    val min = charCounts.minOf { it.second }

    return max - min
}


fun main () {
    println("------------ PART 1 ------------")
    val result1 = calculateDifferenceBetweenMostAndLeastCommonDigit(inputStart, inputReplacements, 10)
    println("result: $result1")
    // 2268 too high

    println("------------ PART 2 ------------")
    val result2 = calculateDifferenceBetweenMostAndLeastCommonDigit(inputStart, inputReplacements, 40)

    println("result: $result2")
}

