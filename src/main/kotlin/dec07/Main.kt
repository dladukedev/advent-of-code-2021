package dec07

import kotlin.math.abs
import kotlin.math.roundToLong


fun fuelCalc(subLocations: List<Int>): Long {
    val max = subLocations
        .maxOrNull() ?: throw Exception("Must Have Values")

    return (0L..max).map { targetLocation ->
        subLocations.sumOf { currentLocation -> abs(currentLocation - targetLocation) }
    }.minOrNull() ?: throw Exception("Must Have Values")
}

fun exponentialFuelCalc(subLocations: List<Int>): Long {
    val max = subLocations
        .maxOrNull() ?: throw Exception("Must Have Values")

    return (0L..max).map { targetLocation ->
        subLocations.sumOf { currentLocation ->
            val distance = abs(currentLocation - targetLocation)
            (distance * (distance+1)) / 2
        }
    }.minOrNull() ?: throw Exception("Must Have Values")
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = fuelCalc(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = exponentialFuelCalc(input)
    println("result: $result2")
}