package dec08

data class Readout(
    val input: List<String>,
    val output: List<String>,
)

fun getCharacterCounts(input: List<String>): Map<Char, Int> {
    return input.flatMap { it.toList() }
        .fold(mutableMapOf()) { acc, chr ->
            acc[chr] = (acc[chr] ?: 0) + 1

            acc
        }
}

fun mapDigits(input: List<String>): Map<String, String> {
    val one = input.single { it.length == 2 }
    val four = input.single { it.length == 4 }
    val seven = input.single { it.length == 3 }
    val eight = input.single { it.length == 7 }

    val fiveLengthDigits = input.filter { it.length == 5 }
    val sixLengthDigits = input.filter { it.length == 6 }

    val characterCounts = getCharacterCounts(input)

    val top = (seven.toList() - one.toList()).single()
    val topLeft = characterCounts.entries.single { it.value == 6 }.key
    val topRight = characterCounts.entries.single { it.value == 8 && it.key != top }.key
    val bottomLeft = characterCounts.entries.single { it.value == 4 }.key
    val bottomRight = characterCounts.entries.single { it.value == 9 }.key
    val middle = four.toList().single { it != topLeft && it != topRight && it != bottomRight }

    val three = fiveLengthDigits.single { !it.contains(topLeft) && !it.contains(bottomLeft) }
    val two = fiveLengthDigits.single { it.contains(bottomLeft) && it != three }
    val five = fiveLengthDigits.single { it.contains(topLeft) && it != three }

    val zero = sixLengthDigits.single { !it.contains(middle) }
    val six = sixLengthDigits.single { !it.contains(topRight) && it.contains(middle) }
    val nine = sixLengthDigits.single { !it.contains(bottomLeft) && it.contains(middle) }

    return mapOf(
        zero.toList().sorted().toString() to "0",
        one.toList().sorted().toString() to "1",
        two.toList().sorted().toString() to "2",
        three.toList().sorted().toString() to "3",
        four.toList().sorted().toString() to "4",
        five.toList().sorted().toString() to "5",
        six.toList().sorted().toString() to "6",
        seven.toList().sorted().toString() to "7",
        eight.toList().sorted().toString() to "8",
        nine.toList().sorted().toString() to "9",
    )
}

fun getOutputSum(input: List<String>): Int {
    return inputAsReadouts(input).sumOf { readout ->
        val digits = mapDigits(readout.input)

        readout.output.map { digits[it.toList().sorted().toString()] }.joinToString("").toInt()
    }
}

fun inputAsReadouts(input: List<String>): List<Readout> {
    return input.map { line ->
        val (input, output) = line.split(" | ")

        Readout(
            input.split(" "),
            output.split(" "),
        )
    }
}

fun count1478InOutput(input: List<String>): Int {
    return inputAsReadouts(input)
        .flatMap { readout -> readout.output }
        .count { digit ->
            when (digit.length) {
                2, 3, 4, 7 -> true
                else -> false
            }
        }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = count1478InOutput(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getOutputSum(input)
    println("result: $result2")
}