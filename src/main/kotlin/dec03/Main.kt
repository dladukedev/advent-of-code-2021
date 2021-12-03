package dec03

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val oldRowLength = this[0].size

    val result = mutableListOf<List<T>>()

    for (oldRowIndex in 0 until oldRowLength) {
        val newRow = mutableListOf<T>()
        for (oldRow in this) {
            newRow.add(oldRow[oldRowIndex]);
        }
        result.add(newRow);
    }

    return result;
}


fun calculateGammaRate(input: List<List<Long>>): Long {
    val halfColumnLength = input.size / 2

    return input.transpose()
        .map {
            // Map to Most Common Digit
            val onesCount = it.count { digit -> digit == 1L }
            if (onesCount > halfColumnLength) {
                1
            } else {
                0
            }
        }.joinToString("")
        .toLong(2)
}

fun calculateEpsilonRate(input: List<List<Long>>): Long {
    val halfColumnLength = input.size / 2

    return input.transpose()
        .map {
            // Map to Most Common Digit
            val onesCount = it.count { digit -> digit == 1L }
            if (onesCount < halfColumnLength) {
                1
            } else {
                0
            }
        }.joinToString("")
        .toLong(2)
}

fun asIntRow(input: String): List<Long> {
    return input
        .toCharArray()
        .map { it.toString().toLong() }
}

fun getPowerConsumption(input: List<String>): Long {
    val parsedInput = input.map { asIntRow(it) }

    println(parsedInput)

    val gamma = calculateGammaRate(parsedInput)
    println("gamma: $gamma")

    val epsilon = calculateEpsilonRate(parsedInput)
    println("epsilon: $epsilon")

    return gamma * epsilon
}

tailrec fun getMinCountsFromList(input: List<String>, currentIndex: Int = 0): String {
    val onesCount = input.filter { it[currentIndex] == '1' }
    val zeroesCount = input.filter { it[currentIndex] == '0' }

    return when {
        zeroesCount.size == 1 && onesCount.isEmpty() -> zeroesCount.single()
        onesCount.size == 1 && zeroesCount.isEmpty() -> onesCount.single()
        onesCount.size < zeroesCount.size -> getMinCountsFromList(onesCount,currentIndex + 1)
        zeroesCount.size < onesCount.size -> getMinCountsFromList(zeroesCount,currentIndex + 1)
        onesCount.size == zeroesCount.size -> getMinCountsFromList(zeroesCount, if(currentIndex + 1 >= input.size ) currentIndex else currentIndex + 1)
        else -> throw Exception("Invalid State")
    }
}

fun getOxygenGeneratorRating(input: List<String>): Long {
    return getMinCountsFromList(input).toLong(2)
}

tailrec fun getMaxCountsFromList(input: List<String>, currentIndex: Int = 0): String {
    val onesCount = input.filter { it[currentIndex] == '1' }
    val zeroesCount = input.filter { it[currentIndex] == '0' }

    return when {
        zeroesCount.size == 1 && onesCount.isEmpty() -> zeroesCount.single()
        onesCount.size == 1 && zeroesCount.isEmpty() -> onesCount.single()
        onesCount.size > zeroesCount.size -> getMaxCountsFromList(onesCount, currentIndex + 1)
        zeroesCount.size > onesCount.size -> getMaxCountsFromList(zeroesCount, currentIndex + 1)
        onesCount.size == zeroesCount.size -> getMaxCountsFromList(onesCount, if(currentIndex + 1 >= input.size ) currentIndex else currentIndex + 1)
        else -> throw Exception("Invalid State")
    }
}

fun getC02ScrubberRating(input: List<String>): Long {
    return getMaxCountsFromList(input).toLong(2)
}

fun getLifeSupportRating(input: List<String>): Long {
    val oxygenGenerator = getOxygenGeneratorRating(input)
    val c02Scrubber = getC02ScrubberRating(input)

    return oxygenGenerator * c02Scrubber
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getPowerConsumption(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getLifeSupportRating(input)
    println("result: $result2")
}